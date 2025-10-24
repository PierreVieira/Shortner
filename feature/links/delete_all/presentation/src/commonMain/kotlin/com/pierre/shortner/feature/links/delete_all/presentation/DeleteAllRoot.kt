package com.pierre.shortner.feature.links.delete_all.presentation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import com.pierre.shortner.feature.links.delete_all.presentation.model.DeleteAllLinksUiEvent
import com.pierre.shortner.feature.links.delete_all.presentation.viewmodel.DeleteAllLinksViewModel
import com.pierre.shortner.model.routes.DeleteAllRoute
import com.pierre.shortner.ui.components.delete_dialog.DeleteConfirmationDialog
import com.pierre.shortner.ui.utils.ActionCollector
import org.koin.compose.viewmodel.koinViewModel
import shortener.feature.links.delete_all.presentation.generated.resources.Res
import shortener.feature.links.delete_all.presentation.generated.resources.delete_all_links_message
import shortener.feature.links.delete_all.presentation.generated.resources.delete_all_links_title

fun NavGraphBuilder.deleteAll(
    navController: NavController
) {
    dialog<DeleteAllRoute> {
        val viewModel = koinViewModel<DeleteAllLinksViewModel>()
        val isConfirmButtonLoading by viewModel.isConfirmButtonLoading.collectAsState()
        ActionCollector(viewModel.navigateBackUiAction) {
            navController.navigateUp()
        }
        DeleteConfirmationDialog(
            isConfirmButtonLoading = isConfirmButtonLoading,
            title = Res.string.delete_all_links_title,
            message = Res.string.delete_all_links_message,
            onConfirm = { viewModel.onEvent(DeleteAllLinksUiEvent.ON_CONFIRM_CLICK) },
            onDismiss = { viewModel.onEvent(DeleteAllLinksUiEvent.ON_CANCEL_CLICK) }
        )
    }
}
