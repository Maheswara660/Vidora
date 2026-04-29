package com.maheswara660.vidora.core.data.mappers

import com.maheswara660.vidora.core.database.entities.AudioStreamInfoEntity
import com.maheswara660.vidora.core.model.AudioStreamInfo

fun AudioStreamInfoEntity.toAudioStreamInfo() = AudioStreamInfo(
    index = index,
    title = title,
    codecName = codecName,
    language = language,
    disposition = disposition,
    bitRate = bitRate,
    sampleFormat = sampleFormat,
    sampleRate = sampleRate,
    channels = channels,
    channelLayout = channelLayout,
)
