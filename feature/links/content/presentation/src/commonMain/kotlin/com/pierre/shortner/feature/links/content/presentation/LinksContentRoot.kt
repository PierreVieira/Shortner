package com.pierre.shortner.feature.links.content.presentation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.navigation.NavController
import com.pierre.shortner.feature.links.content.presentation.component.LinksContent
import com.pierre.shortner.feature.links.content.presentation.model.action.LinksUiAction
import com.pierre.shortner.feature.links.content.presentation.viewmodel.LinksContentViewModel
import com.pierre.shortner.ui.utils.ActionCollector
import kotlinx.coroutines.flow.Flow
import org.jetbrains.compose.resources.getString
import org.koin.compose.viewmodel.koinViewModel
import shortener.feature.links.content.presentation.generated.resources.Res
import shortener.feature.links.content.presentation.generated.resources.copied_to_clipboard

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
private fun LinksContentActionCollector(
    uiActions: Flow<LinksUiAction>,
    snackbarHostState: SnackbarHostState,
    navController: NavController,
) {
    val clipboardManager = LocalClipboardManager.current
    ActionCollector(uiActions) { uiAction ->
        when (uiAction) {
            is LinksUiAction.ShowSnackbar -> snackbarHostState
                .showSnackbar(getString(uiAction.resourceId))

            is LinksUiAction.Navigate -> navController.navigate(uiAction.route)

            is LinksUiAction.CopyToClipboard -> {
                clipboardManager.setText(AnnotatedString(uiAction.text))
                snackbarHostState.showSnackbar(getString(Res.string.copied_to_clipboard))
            }
        }
    }
}
