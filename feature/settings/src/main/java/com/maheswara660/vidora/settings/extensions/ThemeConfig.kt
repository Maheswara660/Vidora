package com.maheswara660.vidora.settings.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.maheswara660.vidora.core.model.ThemeConfig
import com.maheswara660.vidora.core.ui.R

@Composable
fun ThemeConfig.name(): String {
    val stringRes = when (this) {
        ThemeConfig.SYSTEM -> R.string.system_default
        ThemeConfig.OFF -> R.string.light_theme
        ThemeConfig.ON -> R.string.dark_theme_config
        ThemeConfig.AMOLED -> R.string.amoled_black
    }

    return stringResource(id = stringRes)
}
