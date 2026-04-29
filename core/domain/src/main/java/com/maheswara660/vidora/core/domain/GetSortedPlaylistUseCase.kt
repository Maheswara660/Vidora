package com.maheswara660.vidora.core.domain

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import com.maheswara660.vidora.core.common.Dispatcher
import com.maheswara660.vidora.core.common.VidoraDispatchers
import com.maheswara660.vidora.core.common.extensions.getPath
import com.maheswara660.vidora.core.data.repository.PreferencesRepository
import com.maheswara660.vidora.core.model.MediaViewMode
import com.maheswara660.vidora.core.model.Video
import java.io.File
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class GetSortedPlaylistUseCase @Inject constructor(
    private val getSortedVideosUseCase: GetSortedVideosUseCase,
    private val preferencesRepository: PreferencesRepository,
    @ApplicationContext private val context: Context,
    @Dispatcher(VidoraDispatchers.Default) private val defaultDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(uri: Uri): List<Video> = withContext(defaultDispatcher) {
        val path = context.getPath(uri) ?: return@withContext emptyList()
        val parent = File(path).parent.takeIf {
            preferencesRepository.applicationPreferences.first().mediaViewMode != MediaViewMode.VIDEOS
        }

        getSortedVideosUseCase.invoke(parent).first()
    }
}
