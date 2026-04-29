package com.maheswara660.vidora.settings.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.maheswara660.vidora.core.model.FastSeek
import com.maheswara660.vidora.core.ui.R

@Composable
fun FastSeek.name(): String {
    val stringRes = when (this) {
        FastSeek.AUTO -> R.string.auto
        FastSeek.ENABLE -> R.string.enable
        FastSeek.DISABLE -> R.string.disable
    }

    return stringResource(id = stringRes)
}
