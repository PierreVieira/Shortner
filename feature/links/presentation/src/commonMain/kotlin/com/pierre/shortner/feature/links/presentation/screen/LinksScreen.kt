package com.pierre.shortner.feature.links.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pierre.shortner.feature.links.domain.model.Link
import com.pierre.shortner.feature.links.presentation.component.LinkCard
import com.pierre.shortner.feature.links.presentation.component.UrlInputField
import com.pierre.shortner.feature.links.presentation.model.event.LinksUiEvent
import com.pierre.shortner.feature.links.presentation.model.state.LinksUiState
import com.pierre.shortner.ui.components.icon_button.CommonIconButton
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import shortener.feature.links.presentation.generated.resources.Res
import shortener.feature.links.presentation.generated.resources.links_screen_title
import shortener.feature.links.presentation.generated.resources.no_links_message
import shortener.feature.links.presentation.generated.resources.theme_button

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LinksScreen(
    uiState: LinksUiState,
    snackbarHostState: SnackbarHostState,
    onEvent: (LinksUiEvent) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(Res.string.links_screen_title),
                    )
                },
                actions = {
                    CommonIconButton(
                        imageVector = Icons.Default.Palette,
                        contentDescription = stringResource(Res.string.theme_button),
                        onClick = { onEvent(LinksUiEvent.OnThemeClick) }
                    )
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        contentWindowInsets = WindowInsets.safeDrawing,
    ) { paddingValues: PaddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            UrlInputField(
                modifier = Modifier.padding(bottom = 16.dp),
                urlText = uiState.urlText,
                isLoading = uiState.isLoading,
                onEvent = onEvent
            )

            if (uiState.links.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(Res.string.no_links_message),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(bottom = 80.dp) // Space for FAB
                ) {
                    items(uiState.links) { link ->
                        LinkCard(
                            link = link,
                            onDeleteClick = { onEvent(LinksUiEvent.OnDeleteLink(link.id)) }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun LinksScreenPreview() {
    LinksScreen(
        uiState = LinksUiState(
            links = listOf(
                Link(
                    id = 1,
                    originalUrl = "https://www.example.com/very/long/url",
                    shortenedUrl = "https://short.ly/abc123",
                    alias = "abc123"
                )
            ),
            isLoading = false,
            urlText = "",
        ),
        snackbarHostState = remember { SnackbarHostState() },
        onEvent = {}
    )
}
