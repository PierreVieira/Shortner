package com.pierre.shortner.feature.links.content.presentation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.pierre.shortner.feature.links.content.presentation.component.LinksContent
import com.pierre.shortner.feature.links.content.presentation.model.action.LinksUiAction
import com.pierre.shortner.feature.links.content.presentation.viewmodel.LinksViewModel
import com.pierre.shortner.ui.utils.ActionCollector
import kotlinx.coroutines.flow.Flow
import org.jetbrains.compose.resources.getString
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LinksContentRoot(
    navController: NavController,
    snackbarHostState: SnackbarHostState
) {
    val viewModel = koinViewModel<LinksViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    LinksContentActionCollector(
        uiActions = viewModel.uiActions,
        snackbarHostState = snackbarHostState,
        navController = navController,
    )
    LinksContent(
        uiState = uiState,
        onEvent = viewModel::onEvent
    )
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
        }
    }
}
