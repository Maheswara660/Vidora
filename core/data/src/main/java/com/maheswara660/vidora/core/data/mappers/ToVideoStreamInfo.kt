package com.maheswara660.vidora.core.data.mappers

import com.maheswara660.vidora.core.database.entities.VideoStreamInfoEntity
import com.maheswara660.vidora.core.model.VideoStreamInfo

fun VideoStreamInfoEntity.toVideoStreamInfo() = VideoStreamInfo(
    index = index,
    title = title,
    codecName = codecName,
    language = language,
    disposition = disposition,
    bitRate = bitRate,
    frameRate = frameRate,
    frameWidth = frameWidth,
    frameHeight = frameHeight,
)
