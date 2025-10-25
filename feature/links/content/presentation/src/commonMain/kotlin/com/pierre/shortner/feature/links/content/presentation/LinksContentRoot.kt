package com.pierre.shortner.feature.links.content.presentation

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.pierre.shortner.feature.links.content.presentation.component.LoadedLinksContent
import com.pierre.shortner.feature.links.content.presentation.model.action.LinksUiAction
import com.pierre.shortner.feature.links.content.presentation.model.event.LinksUiEvent
import com.pierre.shortner.feature.links.content.presentation.model.state.LinkListUiState
import com.pierre.shortner.feature.links.content.presentation.model.state.LinksContentUiState
import com.pierre.shortner.feature.links.content.presentation.viewmodel.LinksContentViewModel
import com.pierre.shortner.ui.utils.ActionCollector
import kotlinx.coroutines.flow.Flow
import org.jetbrains.compose.resources.getString
import shortener.feature.links.content.presentation.generated.resources.Res
import shortener.feature.links.content.presentation.generated.resources.copied_to_clipboard
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LinksContentRoot(
    navController: NavController,
    snackbarHostState: SnackbarHostState,
) {
    val viewModel = koinViewModel<LinksContentViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    LinksContentActionCollector(
        uiActions = viewModel.uiActions,
        snackbarHostState = snackbarHostState,
        navController = navController,
    )
    LinksContent(uiState, viewModel::onEvent)
}

@Composable
private fun LinksContent(
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

@Composable
private fun LinksContentActionCollector(
    uiActions: Flow<LinksUiAction>,
    snackbarHostState: SnackbarHostState,
    navController: NavController,
) {
    ActionCollector(uiActions) { uiAction ->
        when (uiAction) {
            is LinksUiAction.ShowSnackbar -> snackbarHostState
                .showSnackbar(getString(uiAction.resourceId))

            is LinksUiAction.Navigate -> navController.navigate(uiAction.route)

            is LinksUiAction.CopyToClipboard -> {
                // TODO: Implement platform-specific clipboard functionality
                // For now, show a snackbar to indicate the text was copied
                snackbarHostState.showSnackbar(getString(Res.string.copied_to_clipboard))
            }
        }
    }
}
