package com.maheswara660.vidora.settings.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.maheswara660.vidora.core.model.Resume
import com.maheswara660.vidora.core.ui.R

@Composable
fun Resume.name(): String {
    val stringRes = when (this) {
        Resume.YES -> R.string.yes
        Resume.NO -> R.string.no
    }

    return stringResource(id = stringRes)
}
