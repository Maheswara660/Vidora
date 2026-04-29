package com.maheswara660.vidora.core.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VidoraTopAppBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
    ),
    centered: Boolean = false,
) {
    if (centered) {
        CenterAlignedTopAppBar(
            title = title,
            navigationIcon = navigationIcon,
            actions = actions,
            colors = colors,
            modifier = modifier,
            windowInsets = WindowInsets(top = 0.dp),
        )
    } else {
        TopAppBar(
            title = title,
            navigationIcon = navigationIcon,
            actions = actions,
            colors = colors,
            modifier = modifier,
            windowInsets = WindowInsets(top = 0.dp),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VidoraTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    fontWeight: FontWeight? = null,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
    ),
    centered: Boolean = false,
) {
    VidoraTopAppBar(
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = fontWeight,
            )
        },
        navigationIcon = navigationIcon,
        actions = actions,
        colors = colors,
        modifier = modifier,
        centered = centered,
    )
}
