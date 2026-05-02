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
import com.maheswara660.vidora.core.ui.components.CancelButton
import com.maheswara660.vidora.core.ui.components.DoneButton
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.res.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OptionsDialog(
    text: String,
    onDismissClick: () -> Unit,
    onConfirmClick: (() -> Unit)? = null,
    options: LazyListScope.() -> Unit,
) {
    VidoraBottomSheet(
        onDismissRequest = onConfirmClick ?: onDismissClick,
        title = text,
        confirmButton = onConfirmClick?.let {
            {
                Button(
                    onClick = it,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(com.maheswara660.vidora.core.ui.R.string.apply))
                }
            }
        },
        dismissButton = {
            CancelButton(
                onClick = onDismissClick,
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .selectableGroup(),
            content = options,
        )
    }
}
