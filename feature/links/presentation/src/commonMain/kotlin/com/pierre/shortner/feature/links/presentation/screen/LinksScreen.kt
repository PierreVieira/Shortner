package com.pierre.shortner.feature.links.presentation.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pierre.shortner.feature.links.presentation.component.LinkCard
import com.pierre.shortner.feature.links.presentation.model.LinkPresentationModel
import com.pierre.shortner.feature.links.presentation.model.event.LinksUiEvent
import com.pierre.shortner.feature.links.presentation.model.state.LinksUiState
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import shortener.feature.links.presentation.generated.resources.Res
import shortener.feature.links.presentation.generated.resources.no_links_message

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LinksScreen(
    uiState: LinksUiState,
    snackbarHostState: SnackbarHostState,
    onEvent: (LinksUiEvent) -> Unit,
    topBar: @Composable () -> Unit,
    inputField: @Composable (Modifier) -> Unit,
) {
    Scaffold(
        topBar = topBar,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        contentWindowInsets = WindowInsets.safeDrawing,
    ) { paddingValues: PaddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            inputField(Modifier.padding(bottom = 16.dp),)

            AnimatedContent(
                targetState = uiState.links.isEmpty(),
                transitionSpec = {
                    fadeIn(animationSpec = tween(400)) togetherWith fadeOut(animationSpec = tween(400))
                },
                label = "empty_list_transition"
            ) { isEmpty ->
                if (isEmpty) {
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
                        items(
                            items = uiState.links,
                            key = { it.id },
                        ) { linkPresentationModel ->
                            LinkCard(
                                modifier = Modifier.fillMaxWidth().animateItem(),
                                linkPresentationModel = linkPresentationModel,
                                onEvent = onEvent,
                            )
                        }
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
                LinkPresentationModel(
                    id = 1,
                    originalUrl = "https://www.example.com/very/long/url",
                    shortenedUrl = "https://short.ly/abc123",
                    alias = "abc123",
                    createdAt = "15/01/2024 às 10:30:45",
                    isCardExpanded = false,
                    isMenuExpanded = false
                )
            ),
            isSendButtonLoading = false,
            urlText = "",
        ),
        snackbarHostState = remember { SnackbarHostState() },
        onEvent = {},
        topBar = {},
        inputField = {}
    )
}
