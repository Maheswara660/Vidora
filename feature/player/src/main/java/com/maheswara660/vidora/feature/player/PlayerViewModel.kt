package com.maheswara660.vidora.feature.player

import android.net.Uri
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.maheswara660.vidora.core.data.repository.MediaRepository
import com.maheswara660.vidora.core.data.repository.PreferencesRepository
import com.maheswara660.vidora.core.domain.GetSortedPlaylistUseCase
import com.maheswara660.vidora.core.model.LoopMode
import com.maheswara660.vidora.core.model.PlayerPreferences
import com.maheswara660.vidora.core.model.Video
import com.maheswara660.vidora.core.model.VideoContentScale
import com.maheswara660.vidora.feature.player.state.SubtitleOptionsEvent
import com.maheswara660.vidora.feature.player.state.VideoZoomEvent
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val mediaRepository: MediaRepository,
    private val preferencesRepository: PreferencesRepository,
    private val getSortedPlaylistUseCase: GetSortedPlaylistUseCase,
) : ViewModel() {

    var playWhenReady: Boolean = true

    private val internalUiState = MutableStateFlow(
        PlayerUiState(
            playerPreferences = preferencesRepository.playerPreferences.value,
        ),
    )
    val uiState = internalUiState.asStateFlow()

    init {
        viewModelScope.launch {
            preferencesRepository.playerPreferences.collect { prefs ->
                internalUiState.update { it.copy(playerPreferences = prefs) }
            }
        }
    }

    suspend fun getPlaylistFromUri(uri: Uri): List<Video> {
        return getSortedPlaylistUseCase.invoke(uri)
    }

    fun updateVideoZoom(uri: String, zoom: Float) {
        viewModelScope.launch {
            mediaRepository.updateMediumZoom(uri, zoom)
        }
    }

    fun updatePlayerBrightness(value: Float) {
        viewModelScope.launch {
            preferencesRepository.updatePlayerPreferences { it.copy(playerBrightness = value) }
        }
    }

    fun updateVideoContentScale(contentScale: VideoContentScale) {
        viewModelScope.launch {
            preferencesRepository.updatePlayerPreferences { it.copy(playerVideoZoom = contentScale) }
        }
    }

    fun setLoopMode(loopMode: LoopMode) {
        viewModelScope.launch {
            preferencesRepository.updatePlayerPreferences { it.copy(loopMode = loopMode) }
        }
    }

    fun onVideoZoomEvent(event: VideoZoomEvent) {
        when (event) {
            is VideoZoomEvent.ContentScaleChanged -> {
                updateVideoContentScale(event.contentScale)
            }
            is VideoZoomEvent.ZoomChanged -> {
                updateVideoZoom(event.mediaItem.mediaId, event.zoom)
            }
        }
    }

    fun onSubtitleOptionEvent(event: SubtitleOptionsEvent) {
        when (event) {
            is SubtitleOptionsEvent.DelayChanged -> {
                updateSubtitleDelay(event.mediaItem.mediaId, event.delay)
            }
            is SubtitleOptionsEvent.SpeedChanged -> {
                updateSubtitleSpeed(event.mediaItem.mediaId, event.speed)
            }
        }
    }

    private fun updateSubtitleDelay(uri: String, delay: Long) {
        viewModelScope.launch {
            mediaRepository.updateSubtitleDelay(uri, delay)
        }
    }

    private fun updateSubtitleSpeed(uri: String, speed: Float) {
        viewModelScope.launch {
            mediaRepository.updateSubtitleSpeed(uri, speed)
        }
    }
}

@Stable
data class PlayerUiState(
    val playerPreferences: PlayerPreferences? = null,
)

sealed interface PlayerEvent
