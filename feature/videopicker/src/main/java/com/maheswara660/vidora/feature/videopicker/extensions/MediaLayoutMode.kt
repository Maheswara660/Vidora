package com.maheswara660.vidora.feature.videopicker.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.maheswara660.vidora.core.model.MediaLayoutMode
import com.maheswara660.vidora.core.ui.R

@Composable
fun MediaLayoutMode.name(): String {
    return when (this) {
        MediaLayoutMode.LIST -> stringResource(id = R.string.list)
        MediaLayoutMode.GRID -> stringResource(id = R.string.grid)
    }
}
