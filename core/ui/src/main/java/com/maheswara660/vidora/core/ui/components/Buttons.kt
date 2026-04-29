package com.maheswara660.vidora.core.ui.components

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.maheswara660.vidora.core.ui.R

@Composable
fun DoneButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    TextButton(
        enabled = enabled,
        onClick = onClick,
        modifier = modifier,
    ) {
        Text(text = stringResource(R.string.done))
    }
}

@Composable
fun CancelButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    TextButton(
        enabled = enabled,
        onClick = onClick,
        modifier = modifier,
    ) {
        Text(text = stringResource(R.string.cancel))
    }
}
