package com.maheswara660.vidora.settings.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.maheswara660.vidora.core.model.DoubleTapGesture
import com.maheswara660.vidora.core.ui.R

@Composable
fun DoubleTapGesture.name(): String {
    val stringRes = when (this) {
        DoubleTapGesture.PLAY_PAUSE -> R.string.play_pause
        DoubleTapGesture.FAST_FORWARD_AND_REWIND -> R.string.ff_rewind
        DoubleTapGesture.BOTH -> R.string.play_pause_ff_rewind
        DoubleTapGesture.NONE -> R.string.none
    }

    return stringResource(id = stringRes)
}
