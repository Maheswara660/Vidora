package com.maheswara660.vidora.core.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.maheswara660.vidora.core.database.entities.DirectoryEntity
import com.maheswara660.vidora.core.database.entities.MediumEntity

data class DirectoryWithMedia(
    @Embedded val directory: DirectoryEntity,
    @Relation(
        entity = MediumEntity::class,
        parentColumn = "path",
        entityColumn = "parent_path",
    )
    val media: List<MediumWithInfo>,
)
