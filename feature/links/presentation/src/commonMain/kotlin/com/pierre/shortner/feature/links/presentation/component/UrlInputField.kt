package com.pierre.shortner.feature.links.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pierre.shortner.model.theme.Theme
import com.pierre.shortner.ui.theme.AppTheme
import com.pierre.shortner.ui.theme.preview.AllThemePreferencesPreviewParameterProvider
import com.pierre.shortner.ui.theme.preview.PreviewTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import shortener.feature.links.presentation.generated.resources.Res
import shortener.feature.links.presentation.generated.resources.enter_url_hint
import shortener.feature.links.presentation.generated.resources.shorten_button

@Composable
fun UrlInputField(
    urlText: String,
    isLoading: Boolean,
    onUrlTextChange: (String) -> Unit,
    onShortenClick: () -> Unit,
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
            OutlinedTextField(
                value = urlText,
                onValueChange = onUrlTextChange,
                placeholder = { Text(stringResource(Res.string.enter_url_hint)) },
                modifier = Modifier.weight(1f),
                enabled = !isLoading
            )
            
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(8.dp)
                )
            } else {
                IconButton(
                    onClick = onShortenClick,
                    enabled = urlText.isNotBlank()
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = stringResource(Res.string.shorten_button),
                        tint = if (urlText.isNotBlank()) {
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
        UrlInputField(
            urlText = "https://www.example.com",
            isLoading = false,
            onUrlTextChange = {},
            onShortenClick = {}
        )
    }
}
