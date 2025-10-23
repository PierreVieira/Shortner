package com.pierre.shortner.feature.links.presentation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.pierre.shortner.feature.links.presentation.model.LinksUiAction
import com.pierre.shortner.model.routes.LinksRoute
import com.pierre.shortner.model.routes.theme.ThemeSettingsRoute
import com.pierre.shortner.ui.utils.ActionCollector
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.links(
    navController: NavController,
) {
    composable<LinksRoute> {
        val viewModel = koinViewModel<LinksViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }

        ActionCollector(viewModel.uiActions) { action ->
            when (action) {
                is LinksUiAction.ShowSnackbar -> snackbarHostState.showSnackbar(action.message)
                LinksUiAction.NavigateToTheme -> navController.navigate(ThemeSettingsRoute)
            }
        }

        LinksScreen(
            snackbarHostState = snackbarHostState,
            uiState = uiState,
            onEvent = viewModel::onEvent,
        )
    }
}
