package com.maheswara660.vidora.settings.screens.audio

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.maheswara660.vidora.core.ui.R
import com.maheswara660.vidora.core.ui.components.ClickablePreferenceItem
import com.maheswara660.vidora.core.ui.components.ListSectionTitle
import com.maheswara660.vidora.core.ui.components.VidoraTopAppBar
import com.maheswara660.vidora.core.ui.components.PreferenceSwitch
import com.maheswara660.vidora.core.ui.components.RadioTextButton
import com.maheswara660.vidora.core.ui.designsystem.VidoraIcons
import com.maheswara660.vidora.core.ui.theme.VidoraTheme
import com.maheswara660.vidora.settings.composables.OptionsDialog

@Composable
fun AudioPreferencesScreen(
    onNavigateUp: () -> Unit,
    viewModel: AudioPreferencesViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AudioPreferencesContent(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onNavigateUp = onNavigateUp,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun AudioPreferencesContent(
    uiState: AudioPreferencesUiState,
    onEvent: (AudioPreferencesUiEvent) -> Unit,
    onNavigateUp: () -> Unit,
) {

    Scaffold(
        topBar = {
            VidoraTopAppBar(
                title = stringResource(id = R.string.audio),
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
            ListSectionTitle(text = stringResource(id = R.string.playback))
            Column(
                verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap),
            ) {
                PreferenceSwitch(
                    title = stringResource(R.string.require_audio_focus),
                    description = stringResource(R.string.require_audio_focus_desc),
                    icon = VidoraIcons.Focus,
                    isChecked = uiState.preferences.requireAudioFocus,
                    onClick = { onEvent(AudioPreferencesUiEvent.ToggleRequireAudioFocus) },
                    isFirstItem = true,
                )
                PreferenceSwitch(
                    title = stringResource(id = R.string.pause_on_headset_disconnect),
                    description = stringResource(id = R.string.pause_on_headset_disconnect_desc),
                    icon = VidoraIcons.HeadsetOff,
                    isChecked = uiState.preferences.pauseOnHeadsetDisconnect,
                    onClick = { onEvent(AudioPreferencesUiEvent.TogglePauseOnHeadsetDisconnect) },
                )
                PreferenceSwitch(
                    title = stringResource(id = R.string.system_volume_panel),
                    description = stringResource(id = R.string.system_volume_panel_desc),
                    icon = VidoraIcons.Headset,
                    isChecked = uiState.preferences.showSystemVolumePanel,
                    onClick = { onEvent(AudioPreferencesUiEvent.ToggleShowSystemVolumePanel) },
                )
                PreferenceSwitch(
                    title = stringResource(id = R.string.volume_boost),
                    description = stringResource(id = R.string.volume_boost_desc),
                    icon = VidoraIcons.VolumeUp,
                    isChecked = uiState.preferences.enableVolumeBoost,
                    onClick = { onEvent(AudioPreferencesUiEvent.ToggleVolumeBoost) },
                    isLastItem = true
                )
            }
        }

        uiState.showDialog?.let { showDialog ->
            when (showDialog) {
                else -> {}
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun AudioPreferencesScreenPreview() {
    VidoraTheme {
        AudioPreferencesContent(
            uiState = AudioPreferencesUiState(),
            onNavigateUp = {},
            onEvent = {},
        )
    }
}
