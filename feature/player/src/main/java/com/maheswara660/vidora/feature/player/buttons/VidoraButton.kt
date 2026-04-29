package com.maheswara660.vidora.feature.player.buttons

import androidx.annotation.OptIn
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.compose.state.rememberNextButtonState
import com.maheswara660.vidora.core.ui.R as coreUiR
import com.maheswara660.vidora.feature.player.LocalControlsVisibilityState
import com.maheswara660.vidora.core.ui.designsystem.VidoraIcons

@OptIn(UnstableApi::class)
@Composable
internal fun VidoraButton(player: Player, modifier: Modifier = Modifier) {
    val state = rememberNextButtonState(player)
    val controlsVisibilityState = LocalControlsVisibilityState.current

    PlayerButton(
        modifier = modifier.size(48.dp),
        isEnabled = state.isEnabled,
        onClick = {
            state.onClick()
            controlsVisibilityState?.showControls()
        },
    ) {
        Icon(
            imageVector = VidoraIcons.SkipNext,
            contentDescription = stringResource(coreUiR.string.player_controls_next),
            modifier = Modifier.size(32.dp),
        )
    }
}
