package com.maheswara660.vidora.settings.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.maheswara660.vidora.core.model.ControlButtonsPosition
import com.maheswara660.vidora.core.ui.R

@Composable
fun ControlButtonsPosition.name(): String {
    val stringRes = when (this) {
        ControlButtonsPosition.LEFT -> R.string.control_buttons_alignment_left
        ControlButtonsPosition.RIGHT -> R.string.control_buttons_alignment_right
    }

    return stringResource(stringRes)
}
