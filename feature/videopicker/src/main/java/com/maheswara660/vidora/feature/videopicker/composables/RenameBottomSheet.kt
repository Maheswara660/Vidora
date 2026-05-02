package com.maheswara660.vidora.feature.videopicker.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.maheswara660.vidora.core.ui.R
import com.maheswara660.vidora.core.ui.components.CancelButton
import com.maheswara660.vidora.core.ui.components.DoneButton
import androidx.compose.ui.text.font.FontWeight
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.delay
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.BottomSheetDefaults
import com.maheswara660.vidora.core.ui.components.VidoraBottomSheet
import androidx.compose.material3.Button

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RenameBottomSheet(
    name: String,
    onDismiss: () -> Unit,
    onDone: (String) -> Unit,
) {
    var mediaName by rememberSaveable { mutableStateOf(name) }
    val focusRequester = remember { FocusRequester() }
    val sheetState = rememberModalBottomSheetState()

    VidoraBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        title = stringResource(R.string.rename_to),
        confirmButton = {
            Button(
                enabled = mediaName.isNotBlank(),
                onClick = { onDone(mediaName) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.done))
            }
        },
        dismissButton = {
            CancelButton(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = mediaName,
                onValueChange = { mediaName = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                singleLine = true,
                shape = MaterialTheme.shapes.medium
            )
        }
    }

    LaunchedEffect(Unit) {
        delay(200.milliseconds)
        focusRequester.requestFocus()
    }
}
