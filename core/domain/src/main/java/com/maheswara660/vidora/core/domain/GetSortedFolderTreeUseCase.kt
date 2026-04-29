package com.maheswara660.vidora.core.domain

import com.maheswara660.vidora.core.common.Dispatcher
import com.maheswara660.vidora.core.common.VidoraDispatchers
import com.maheswara660.vidora.core.data.repository.MediaRepository
import com.maheswara660.vidora.core.data.repository.PreferencesRepository
import com.maheswara660.vidora.core.model.ApplicationPreferences
import com.maheswara660.vidora.core.model.Folder
import com.maheswara660.vidora.core.model.Sort
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn

class GetSortedFolderTreeUseCase @Inject constructor(
    private val mediaRepository: MediaRepository,
    private val preferencesRepository: PreferencesRepository,
    @Dispatcher(VidoraDispatchers.Default) private val defaultDispatcher: CoroutineDispatcher,
) {
    operator fun invoke(folderPath: String? = null): Flow<Folder?> = combine(
        mediaRepository.getFoldersFlow(),
        preferencesRepository.applicationPreferences,
    ) { folders, preferences ->
        val currentFolder = folderPath?.let {
            folders.find { it.path == folderPath } ?: return@combine null
        } ?: Folder.rootFolder

        val sort = Sort(by = preferences.sortBy, order = preferences.sortOrder)

        currentFolder.copy(
            folderList = folders.getFoldersFor(path = currentFolder.path, preferences = preferences),
        ).let { folder ->
            if (folderPath == null) folder.getInitialFolderWithContent() else folder
        }.let { folder ->
            folder.copy(
                mediaList = folder.mediaList.sortedWith(sort.videoComparator()),
                folderList = folder.folderList.sortedWith(sort.folderComparator()),
            )
        }
    }.flowOn(defaultDispatcher)

    private fun Folder.getInitialFolderWithContent(): Folder {
        return when {
            mediaList.isEmpty() && folderList.size == 1 -> folderList.first().getInitialFolderWithContent()
            else -> this
        }
    }

    private fun List<Folder>.getFoldersFor(
        path: String,
        preferences: ApplicationPreferences,
    ): List<Folder> {
        return filter {
            it.parentPath == path && it.path !in preferences.excludeFolders
        }.map { directory ->
            Folder(
                name = directory.name,
                path = directory.path,
                dateModified = directory.dateModified,
                mediaList = directory.mediaList,
                folderList = getFoldersFor(path = directory.path, preferences = preferences),
            )
        }
    }
}
