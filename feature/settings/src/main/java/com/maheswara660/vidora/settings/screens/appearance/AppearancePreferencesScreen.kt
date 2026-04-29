package com.maheswara660.vidora.settings.screens.appearance

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.maheswara660.vidora.core.model.ThemeConfig
import com.maheswara660.vidora.core.ui.R
import com.maheswara660.vidora.core.ui.components.ListSectionTitle
import com.maheswara660.vidora.core.ui.components.VidoraTopAppBar
import com.maheswara660.vidora.core.ui.components.PreferenceSwitch
import com.maheswara660.vidora.core.ui.components.RadioTextButton
import com.maheswara660.vidora.core.ui.designsystem.VidoraIcons
import com.maheswara660.vidora.core.ui.theme.VidoraTheme
import com.maheswara660.vidora.core.ui.theme.supportsDynamicTheming
import com.maheswara660.vidora.settings.extensions.name
import com.maheswara660.vidora.core.ui.theme.CustomAccents
import com.maheswara660.vidora.core.ui.components.ClickablePreferenceItem
import com.maheswara660.vidora.core.ui.components.VidoraBottomSheet
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.draw.clip
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableIntStateOf

@Composable
fun AppearancePreferencesScreen(
    onNavigateUp: () -> Unit,
    viewModel: AppearancePreferencesViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AppearancePreferencesContent(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onNavigateUp = onNavigateUp,
    )
}



@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun AppearancePreferencesContent(
    uiState: AppearancePreferencesUiState,
    onEvent: (AppearancePreferencesEvent) -> Unit,
    onNavigateUp: () -> Unit = {},
) {
    var showThemeSheet by remember { mutableStateOf(false) }
    var showAccentSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            VidoraTopAppBar(
                title = stringResource(id = R.string.themes),
                centered = true,
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = VidoraIcons.ArrowBack,
                            contentDescription = stringResource(id = R.string.navigate_up),
                        )
                    }
                },
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState())
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
        ) {
            ListSectionTitle(text = stringResource(id = R.string.interface_name))
            Column(
                verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap),
            ) {
                ClickablePreferenceItem(
                    title = stringResource(id = R.string.themes),
                    description = uiState.preferences.themeConfig.name(),
                    icon = VidoraIcons.DarkMode,
                    onClick = { showThemeSheet = true },
                    isFirstItem = true
                )
                ClickablePreferenceItem(
                    title = stringResource(id = R.string.select_accent),
                    description = when (uiState.preferences.accentColorIndex) {
                        -1 -> stringResource(R.string.material_you)
                        0 -> stringResource(R.string.default_teal)
                        else -> CustomAccents.getOrNull(uiState.preferences.accentColorIndex - 1)?.name ?: ""
                    },
                    icon = VidoraIcons.Palette,
                    onClick = { showAccentSheet = true },
                    isLastItem = true
                )
            }
        }

        if (showThemeSheet) {
            ThemeBottomSheet(
                initialConfig = uiState.preferences.themeConfig,
                onDismiss = { showThemeSheet = false },
                onApply = { config ->
                    onEvent(AppearancePreferencesEvent.UpdateThemeConfig(config))
                    showThemeSheet = false
                }
            )
        }

        if (showAccentSheet) {
            AccentBottomSheet(
                initialAccentIndex = uiState.preferences.accentColorIndex,
                onDismiss = { showAccentSheet = false },
                onApply = { index ->
                    onEvent(AppearancePreferencesEvent.UpdateAccentColorIndex(index))
                    showAccentSheet = false
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ThemeBottomSheet(
    initialConfig: ThemeConfig,
    onDismiss: () -> Unit,
    onApply: (ThemeConfig) -> Unit,
) {
    var selectedConfig by remember { mutableStateOf(initialConfig) }

    VidoraBottomSheet(
        onDismissRequest = onDismiss,
        title = stringResource(R.string.select_theme),
        confirmButton = {
            Button(onClick = { onApply(selectedConfig) }) {
                Text(stringResource(R.string.apply))
            }
        },
        dismissButton = {
            androidx.compose.material3.TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp)
        ) {
            ThemeConfig.entries.forEach { config ->
                RadioTextButton(
                    text = config.name(),
                    selected = selectedConfig == config,
                    onClick = { selectedConfig = config }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AccentBottomSheet(
    initialAccentIndex: Int,
    onDismiss: () -> Unit,
    onApply: (Int) -> Unit,
) {
    var selectedIndex by remember { mutableIntStateOf(initialAccentIndex) }

    VidoraBottomSheet(
        onDismissRequest = onDismiss,
        title = stringResource(R.string.select_accent),
        confirmButton = {
            Button(onClick = { onApply(selectedIndex) }) {
                Text(stringResource(R.string.apply))
            }
        },
        dismissButton = {
            androidx.compose.material3.TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp)
        ) {
            if (supportsDynamicTheming()) {
                RadioTextButton(
                    text = stringResource(R.string.material_you),
                    selected = selectedIndex == -1,
                    onClick = { selectedIndex = -1 }
                )
            }
            RadioTextButton(
                text = stringResource(R.string.default_teal),
                selected = selectedIndex == 0,
                onClick = { selectedIndex = 0 }
            )
            
            CustomAccents.forEachIndexed { index, accent ->
                RadioTextButton(
                    text = accent.name,
                    selected = selectedIndex == index + 1,
                    onClick = { selectedIndex = index + 1 }
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun AppearancePreferencesScreenPreview() {
    VidoraTheme {
        AppearancePreferencesContent(
            uiState = AppearancePreferencesUiState(),
            onEvent = {},
        )
    }
}
