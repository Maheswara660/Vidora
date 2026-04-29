package com.maheswara660.vidora.feature.player.extensions

import androidx.media3.common.VideoSize

val VideoSize.isPortrait: Boolean
    get() = this.height > this.width
