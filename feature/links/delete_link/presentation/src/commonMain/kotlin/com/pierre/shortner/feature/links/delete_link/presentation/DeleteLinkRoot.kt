package com.pierre.shortner.feature.links.delete_link.presentation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.dialog
import com.pierre.shortner.feature.links.delete_link.presentation.model.DeleteLinkEvent
import com.pierre.shortner.model.routes.links.delete.DeleteLinkRoute
import com.pierre.shortner.ui.components.delete_dialog.DeleteConfirmationDialog
import com.pierre.shortner.ui.utils.ActionCollector
import org.koin.compose.viewmodel.koinViewModel
import shortener.feature.links.delete_link.presentation.generated.resources.Res
import shortener.feature.links.delete_link.presentation.generated.resources.delete_link_message
import shortener.feature.links.delete_link.presentation.generated.resources.delete_link_title

fun NavGraphBuilder.deleteLink(navController: NavHostController) {
    dialog<DeleteLinkRoute> {
        val viewModel = koinViewModel<DeleteLinkViewModel>()
        val onEvent = viewModel::onEvent
        val isLoading by viewModel.uiState.collectAsState()
        ActionCollector(viewModel.navigateBack) {
            navController.navigateUp()
        }
        DeleteConfirmationDialog(
            title = Res.string.delete_link_title,
            message = Res.string.delete_link_message,
            isConfirmButtonLoading = isLoading,
            onConfirm = {
                onEvent(DeleteLinkEvent.ON_CONFIRM_CLICK)
            },
            onDismiss = {
                onEvent(DeleteLinkEvent.ON_CANCEL_CLICK)
            }
        )
    }
}
