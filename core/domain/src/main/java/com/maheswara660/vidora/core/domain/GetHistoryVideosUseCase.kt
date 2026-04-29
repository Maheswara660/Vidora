package com.maheswara660.vidora.core.domain

import com.maheswara660.vidora.core.common.Dispatcher
import com.maheswara660.vidora.core.common.VidoraDispatchers
import com.maheswara660.vidora.core.data.repository.MediaRepository
import com.maheswara660.vidora.core.model.Video
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.flowOn

class GetHistoryVideosUseCase @Inject constructor(
    private val mediaRepository: MediaRepository,
    @Dispatcher(VidoraDispatchers.Default) private val defaultDispatcher: CoroutineDispatcher,
) {

    operator fun invoke(): Flow<List<Video>> {
        return mediaRepository.getVideosFlow().map { videos ->
            videos.filter { it.lastPlayedAt != null }
                .sortedByDescending { it.lastPlayedAt?.time }
        }.flowOn(defaultDispatcher)
    }
}
