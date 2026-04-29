package com.maheswara660.vidora.core.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.maheswara660.vidora.core.database.entities.AudioStreamInfoEntity
import com.maheswara660.vidora.core.database.entities.MediumEntity
import com.maheswara660.vidora.core.database.entities.MediumStateEntity
import com.maheswara660.vidora.core.database.entities.SubtitleStreamInfoEntity
import com.maheswara660.vidora.core.database.entities.VideoStreamInfoEntity

data class MediumWithInfo(
    @Embedded val mediumEntity: MediumEntity,
    @Relation(
        parentColumn = "uri",
        entityColumn = "uri",
    )
    val mediumStateEntity: MediumStateEntity?,
    @Relation(
        parentColumn = "uri",
        entityColumn = "medium_uri",
    )
    val videoStreamInfo: VideoStreamInfoEntity?,
    @Relation(
        parentColumn = "uri",
        entityColumn = "medium_uri",
    )
    val audioStreamsInfo: List<AudioStreamInfoEntity>,
    @Relation(
        parentColumn = "uri",
        entityColumn = "medium_uri",
    )
    val subtitleStreamsInfo: List<SubtitleStreamInfoEntity>,
)
