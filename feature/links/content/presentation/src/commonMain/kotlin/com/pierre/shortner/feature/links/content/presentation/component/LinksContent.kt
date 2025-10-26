package com.pierre.shortner.feature.links.content.presentation.component

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import com.pierre.shortner.feature.links.content.presentation.model.event.LinksUiEvent
import com.pierre.shortner.feature.links.content.presentation.model.state.LinkListUiState
import com.pierre.shortner.feature.links.content.presentation.model.state.LinksContentUiState

@Composable
internal fun LinksContent(
    uiState: LinksContentUiState,
    onEvent: (LinksUiEvent) -> Unit,
) {
    val linksUiState = uiState.links
    when (linksUiState) {
        is LinkListUiState.Loaded -> LoadedLinksContent(
            links = linksUiState.data,
            onEvent = onEvent,
        )

        LinkListUiState.Loading -> CircularProgressIndicator()
    }
}
