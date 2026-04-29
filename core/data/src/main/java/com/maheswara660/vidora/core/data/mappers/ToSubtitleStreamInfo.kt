package com.maheswara660.vidora.core.data.mappers

import com.maheswara660.vidora.core.database.entities.SubtitleStreamInfoEntity
import com.maheswara660.vidora.core.model.SubtitleStreamInfo

fun SubtitleStreamInfoEntity.toSubtitleStreamInfo() = SubtitleStreamInfo(
    index = index,
    title = title,
    codecName = codecName,
    language = language,
    disposition = disposition,
)
