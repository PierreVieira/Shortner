package com.pierre.shortner.feature.links.presentation.root

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import com.pierre.shortner.feature.links.presentation.model.event.DeleteAllLinksUiEvent
import com.pierre.shortner.feature.links.presentation.viewmodel.DeleteAllLinksViewModel
import com.pierre.shortner.model.routes.DeleteAllRoute
import com.pierre.shortner.ui.components.delete_dialog.DeleteConfirmationDialog
import com.pierre.shortner.ui.utils.ActionCollector
import org.koin.compose.viewmodel.koinViewModel
import shortener.feature.links.presentation.generated.resources.Res
import shortener.feature.links.presentation.generated.resources.delete_all_links_message
import shortener.feature.links.presentation.generated.resources.delete_all_links_title

fun NavGraphBuilder.deleteAll(navController: NavController) {
    dialog<DeleteAllRoute> {
        val viewModel = koinViewModel<DeleteAllLinksViewModel>()
        ActionCollector(viewModel.navigateBackUiAction) {
            navController.navigateUp()
        }
        DeleteConfirmationDialog(
            title = Res.string.delete_all_links_title,
            message = Res.string.delete_all_links_message,
            onConfirm = { viewModel.onEvent(DeleteAllLinksUiEvent.OnConfirmClick) },
            onDismiss = { viewModel.onEvent(DeleteAllLinksUiEvent.OnCancelClick) }
        )
    }
}
