package com.pierre.shortner.feature.links.presentation.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import shortener.feature.links.presentation.generated.resources.Res
import shortener.feature.links.presentation.generated.resources.delete_confirmation_cancel
import shortener.feature.links.presentation.generated.resources.delete_confirmation_confirm
import shortener.feature.links.presentation.generated.resources.delete_confirmation_message
import shortener.feature.links.presentation.generated.resources.delete_confirmation_title

@Composable
fun DeleteConfirmationDialog(
    title: StringResource,
    message: StringResource,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(stringResource(title))
        },
        text = {
            Text(stringResource(message))
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(stringResource(Res.string.delete_confirmation_confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(Res.string.delete_confirmation_cancel))
            }
        }
    )
}
