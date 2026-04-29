package com.maheswara660.vidora.core.domain

import com.maheswara660.vidora.core.common.Dispatcher
import com.maheswara660.vidora.core.common.VidoraDispatchers
import com.maheswara660.vidora.core.data.repository.MediaRepository
import com.maheswara660.vidora.core.data.repository.PreferencesRepository
import com.maheswara660.vidora.core.model.Sort
import com.maheswara660.vidora.core.model.Video
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn

class GetSortedVideosUseCase @Inject constructor(
    private val mediaRepository: MediaRepository,
    private val preferencesRepository: PreferencesRepository,
    @Dispatcher(VidoraDispatchers.Default) private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
) {

    operator fun invoke(folderPath: String? = null): Flow<List<Video>> {
        val videosFlow = if (folderPath != null) {
            mediaRepository.getVideosFlowFromFolderPath(folderPath)
        } else {
            mediaRepository.getVideosFlow()
        }

        return combine(
            videosFlow,
            preferencesRepository.applicationPreferences,
        ) { videoItems, preferences ->

            val nonExcludedVideos = videoItems.filterNot {
                it.parentPath in preferences.excludeFolders
            }

            val sort = Sort(by = preferences.sortBy, order = preferences.sortOrder)
            nonExcludedVideos.sortedWith(sort.videoComparator())
        }.flowOn(defaultDispatcher)
    }
}
