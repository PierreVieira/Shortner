package com.pierre.shortner.feature.links.input.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pierre.shortner.feature.links.input.presentation.model.LinkInputUiEvent
import com.pierre.shortner.feature.links.input.presentation.model.LinkInputUiState
import com.pierre.shortner.model.theme.Theme
import com.pierre.shortner.ui.components.icon_button.CommonIconButton
import com.pierre.shortner.ui.theme.preview.AllThemePreferencesPreviewParameterProvider
import com.pierre.shortner.ui.theme.preview.PreviewTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import shortener.feature.links.input.presentation.generated.resources.Res
import shortener.feature.links.input.presentation.generated.resources.enter_url_hint
import shortener.feature.links.input.presentation.generated.resources.shorten_button

@Composable
fun UrlInputFieldComponent(
    uiState: LinkInputUiState,
    onEvent: (LinkInputUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            uiState.run {
                OutlinedTextField(
                    value = urlText,
                    onValueChange = {
                        onEvent(LinkInputUiEvent.OnUrlTextChange(it))
                    },
                    placeholder = { Text(stringResource(Res.string.enter_url_hint)) },
                    modifier = Modifier.weight(1f),
                    enabled = !isSendButtonLoading
                )
                if (isSendButtonLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(8.dp)
                    )
                } else {
                    val isSendButtonEnabled = urlText.isNotBlank()
                    CommonIconButton(
                        onClick = {
                            onEvent(LinkInputUiEvent.OnShortenUrlClick)
                        },
                        isEnabled = isSendButtonEnabled,
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = stringResource(Res.string.shorten_button),
                        tint = if (isSendButtonEnabled) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                }
            }

        }
    }
}

@Preview
@Composable
private fun UrlInputFieldPreview(
    @PreviewParameter(AllThemePreferencesPreviewParameterProvider::class)
    currentTheme: Theme,
) {
    PreviewTheme(currentTheme) {
        UrlInputFieldComponent(
            uiState = LinkInputUiState(
                urlText = "https://www.google.com",
                isSendButtonLoading = false
            ),
            onEvent = {}
        )
    }
}
