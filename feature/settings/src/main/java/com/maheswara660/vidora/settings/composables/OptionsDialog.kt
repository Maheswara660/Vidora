package com.maheswara660.vidora.settings.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import com.maheswara660.vidora.core.ui.components.VidoraBottomSheet
import androidx.compose.material3.ExperimentalMaterial3Api

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OptionsDialog(
    text: String,
    onDismissClick: () -> Unit,
    options: LazyListScope.() -> Unit,
) {
    VidoraBottomSheet(
        onDismissRequest = onDismissClick,
        title = text,
        dismissButton = {
            androidx.compose.material3.TextButton(onClick = onDismissClick) {
                Text(com.maheswara660.vidora.core.ui.R.string.cancel.let { androidx.compose.ui.res.stringResource(it) })
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.selectableGroup(),
            content = options,
        )
    }
}
