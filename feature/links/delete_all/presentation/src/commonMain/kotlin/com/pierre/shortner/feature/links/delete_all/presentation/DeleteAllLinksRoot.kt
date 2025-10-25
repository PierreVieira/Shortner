package com.pierre.shortner.feature.links.delete_all.presentation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import com.pierre.shortner.feature.links.delete_all.presentation.model.DeleteAllLinksUiEvent
import com.pierre.shortner.feature.links.delete_all.presentation.viewmodel.DeleteAllLinksViewModel
import com.pierre.shortner.model.routes.links.delete.DeleteAllLinksRoute
import com.pierre.shortner.ui.components.delete_dialog.DeleteConfirmationDialog
import com.pierre.shortner.ui.utils.ActionCollector
import org.koin.compose.viewmodel.koinViewModel
import shortener.feature.links.delete_all.presentation.generated.resources.Res
import shortener.feature.links.delete_all.presentation.generated.resources.delete_all_links_message
import shortener.feature.links.delete_all.presentation.generated.resources.delete_all_links_title

fun NavGraphBuilder.deleteAllLinks(
    navController: NavController
) {
    dialog<DeleteAllLinksRoute> {
        val viewModel = koinViewModel<DeleteAllLinksViewModel>()
        val onEvent = viewModel::onEvent
        val isConfirmButtonLoading by viewModel.isConfirmButtonLoading.collectAsState()
        ActionCollector(viewModel.navigateBackUiAction) {
            navController.navigateUp()
        }
        DeleteConfirmationDialog(
            isConfirmButtonLoading = isConfirmButtonLoading,
            title = Res.string.delete_all_links_title,
            message = Res.string.delete_all_links_message,
            onConfirm = { onEvent(DeleteAllLinksUiEvent.ON_CONFIRM_CLICK) },
            onDismiss = { onEvent(DeleteAllLinksUiEvent.ON_CANCEL_CLICK) }
        )
    }
}
