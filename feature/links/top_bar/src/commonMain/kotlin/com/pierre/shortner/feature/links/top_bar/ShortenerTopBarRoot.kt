package com.pierre.shortner.feature.links.top_bar

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.pierre.shortner.feature.links.top_bar.model.ShortenerTopBarUiAction
import com.pierre.shortner.feature.links.top_bar.viewmodel.ShortenerTopBarViewModel
import com.pierre.shortner.ui.utils.ActionCollector
import org.jetbrains.compose.resources.getString
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ShortenerTopAppBarRoot(
    navController: NavController,
    snackbarHostState: SnackbarHostState,
) {
    val viewModel: ShortenerTopBarViewModel = koinViewModel()
    val buttons by viewModel.uiState.collectAsState()
    ActionCollector(viewModel.uiAction) { uiAction ->
        when(uiAction) {
            is ShortenerTopBarUiAction.NavigateToRoute -> navController.navigate(uiAction.route)
            is ShortenerTopBarUiAction.ShowSnackbar ->
                snackbarHostState.showSnackbar(getString(uiAction.stringResource))
        }
    }
    ShortenerTopAppBar(
        buttons = buttons,
        onEvent = viewModel::onEvent
    )
}
