package com.maheswara660.vidora.core.data.mappers

import com.maheswara660.vidora.core.common.Utils
import com.maheswara660.vidora.core.database.relations.DirectoryWithMedia
import com.maheswara660.vidora.core.database.relations.MediumWithInfo
import com.maheswara660.vidora.core.model.Folder

fun DirectoryWithMedia.toFolder() = Folder(
    name = directory.name,
    path = directory.path,
    dateModified = directory.modified,
    parentPath = directory.parentPath,
    formattedMediaSize = Utils.formatFileSize(media.sumOf { it.mediumEntity.size }),
    mediaList = media.map(MediumWithInfo::toVideo),
)
