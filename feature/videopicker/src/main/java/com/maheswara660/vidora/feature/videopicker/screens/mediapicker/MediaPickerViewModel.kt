package com.maheswara660.vidora.feature.videopicker.screens.mediapicker

import android.net.Uri
import androidx.compose.runtime.Stable
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.maheswara660.vidora.core.common.extensions.prettyName
import com.maheswara660.vidora.core.data.repository.PreferencesRepository
import com.maheswara660.vidora.core.domain.GetSortedMediaUseCase
import com.maheswara660.vidora.core.domain.GetSortedVideosUseCase
import com.maheswara660.vidora.core.media.services.MediaService
import com.maheswara660.vidora.core.media.sync.MediaInfoSynchronizer
import com.maheswara660.vidora.core.media.sync.MediaSynchronizer
import com.maheswara660.vidora.core.domain.GetHistoryVideosUseCase
import com.maheswara660.vidora.core.model.ApplicationPreferences
import com.maheswara660.vidora.core.model.Folder
import com.maheswara660.vidora.core.ui.base.DataState
import com.maheswara660.vidora.feature.videopicker.navigation.MediaPickerRoute
import com.maheswara660.vidora.feature.videopicker.navigation.MediaPickerScreenType
import androidx.navigation.toRoute
import java.io.File
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MediaPickerViewModel @Inject constructor(
    private val getSortedMediaUseCase: GetSortedMediaUseCase,
    private val getSortedVideosUseCase: GetSortedVideosUseCase,
    private val getHistoryVideosUseCase: GetHistoryVideosUseCase,
    savedStateHandle: SavedStateHandle,
    private val mediaService: MediaService,
    private val preferencesRepository: PreferencesRepository,
    private val mediaInfoSynchronizer: MediaInfoSynchronizer,
    private val mediaSynchronizer: MediaSynchronizer,
) : ViewModel() {

    private val route = runCatching { savedStateHandle.toRoute<MediaPickerRoute>() }.getOrNull()
    private val folderPath = route?.folderId?.let { Uri.decode(it) }

    private val uiStateInternal = MutableStateFlow(
        MediaPickerUiState(
            folderName = folderPath?.let { File(folderPath).prettyName },
            screenType = MediaPickerScreenType.HOME,
            preferences = preferencesRepository.applicationPreferences.value,
        ),
    )
    val uiState = uiStateInternal.asStateFlow()

    private var loadMediaJob: kotlinx.coroutines.Job? = null

    init {
        loadMedia()

        viewModelScope.launch {
            preferencesRepository.applicationPreferences.collect {
                uiStateInternal.update { currentState ->
                    currentState.copy(
                        preferences = it,
                    )
                }
            }
        }
    }

    fun onEvent(event: MediaPickerUiEvent) {
        when (event) {
            is MediaPickerUiEvent.DeleteFolders -> deleteFolders(event.folders)
            is MediaPickerUiEvent.DeleteVideos -> deleteVideos(event.videos)
            is MediaPickerUiEvent.ShareVideos -> shareVideos(event.videos)
            is MediaPickerUiEvent.Refresh -> refresh()
            is MediaPickerUiEvent.RenameVideo -> renameVideo(event.uri, event.to)
            is MediaPickerUiEvent.AddToSync -> addToMediaInfoSynchronizer(event.uri)
            is MediaPickerUiEvent.UpdateMenu -> updateMenu(event.preferences)
            is MediaPickerUiEvent.UpdateScreenType -> {
                uiStateInternal.update { it.copy(screenType = event.screenType, searchQuery = "") }
                loadMedia()
            }
            is MediaPickerUiEvent.UpdateSearchQuery -> {
                uiStateInternal.update { it.copy(searchQuery = event.query) }
                loadMedia()
            }
        }
    }

    private fun loadMedia() {
        val currentUiState = uiStateInternal.value
        val query = currentUiState.searchQuery

        loadMediaJob?.cancel()
        loadMediaJob = viewModelScope.launch {
            when (currentUiState.screenType) {
                MediaPickerScreenType.HOME -> {
                    getSortedMediaUseCase(folderPath).collect { folder ->
                        val filteredFolder = if (query.isBlank()) folder else {
                            folder?.copy(
                                folderList = folder.folderList.filter { it.name.contains(query, ignoreCase = true) },
                                mediaList = folder.mediaList.filter { it.displayName.contains(query, ignoreCase = true) }
                            )
                        }
                        uiStateInternal.update { it.copy(mediaDataState = DataState.Success(filteredFolder)) }
                    }
                }
                MediaPickerScreenType.VIDEOS -> {
                    getSortedVideosUseCase(null).collect { videos ->
                        val filteredVideos = if (query.isBlank()) videos else {
                            videos.filter { it.displayName.contains(query, ignoreCase = true) }
                        }
                        uiStateInternal.update {
                            it.copy(
                                mediaDataState = DataState.Success(
                                    Folder.rootFolder.copy(
                                        name = "Videos",
                                        mediaList = filteredVideos,
                                        folderList = emptyList()
                                    )
                                )
                            )
                        }
                    }
                }
                MediaPickerScreenType.HISTORY -> {
                    getHistoryVideosUseCase().collect { videos ->
                        val filteredVideos = if (query.isBlank()) videos else {
                            videos.filter { it.displayName.contains(query, ignoreCase = true) }
                        }
                        uiStateInternal.update {
                            it.copy(
                                mediaDataState = DataState.Success(
                                    Folder.rootFolder.copy(
                                        name = "History",
                                        mediaList = filteredVideos,
                                        folderList = emptyList()
                                    )
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private fun deleteFolders(folders: List<Folder>) {
        viewModelScope.launch {
            val uris = folders.flatMap { folder ->
                folder.allMediaList.map { video ->
                    video.uriString.toUri()
                }
            }
            mediaService.deleteMedia(uris)
        }
    }

    private fun deleteVideos(uris: List<String>) {
        viewModelScope.launch {
            mediaService.deleteMedia(uris.map { it.toUri() })
        }
    }

    private fun shareVideos(uris: List<String>) {
        viewModelScope.launch {
            mediaService.shareMedia(uris.map { it.toUri() })
        }
    }

    private fun addToMediaInfoSynchronizer(uri: Uri) {
        viewModelScope.launch {
            mediaInfoSynchronizer.sync(uri)
        }
    }

    private fun renameVideo(uri: Uri, to: String) {
        viewModelScope.launch {
            mediaService.renameMedia(uri, to)
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            uiStateInternal.update { it.copy(refreshing = true) }
            mediaSynchronizer.refresh()
            uiStateInternal.update { it.copy(refreshing = false) }
        }
    }

    private fun updateMenu(preferences: ApplicationPreferences) {
        viewModelScope.launch {
            preferencesRepository.updateApplicationPreferences { preferences }
        }
    }
}

@Stable
data class MediaPickerUiState(
    val folderName: String?,
    val screenType: MediaPickerScreenType = MediaPickerScreenType.HOME,
    val mediaDataState: DataState<Folder?> = DataState.Loading,
    val refreshing: Boolean = false,
    val preferences: ApplicationPreferences = ApplicationPreferences(),
    val searchQuery: String = "",
)

sealed interface MediaPickerUiEvent {
    data class DeleteVideos(val videos: List<String>) : MediaPickerUiEvent
    data class DeleteFolders(val folders: List<Folder>) : MediaPickerUiEvent
    data class ShareVideos(val videos: List<String>) : MediaPickerUiEvent
    data object Refresh : MediaPickerUiEvent
    data class RenameVideo(val uri: Uri, val to: String) : MediaPickerUiEvent
    data class AddToSync(val uri: Uri) : MediaPickerUiEvent
    data class UpdateMenu(val preferences: ApplicationPreferences) : MediaPickerUiEvent
    data class UpdateScreenType(val screenType: MediaPickerScreenType) : MediaPickerUiEvent
    data class UpdateSearchQuery(val query: String) : MediaPickerUiEvent
}
