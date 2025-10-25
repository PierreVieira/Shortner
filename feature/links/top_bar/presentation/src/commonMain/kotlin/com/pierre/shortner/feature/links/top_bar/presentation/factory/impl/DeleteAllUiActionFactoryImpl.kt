package com.pierre.shortner.feature.links.top_bar.presentation.factory.impl

import com.pierre.shortner.feature.links.top_bar.domain.usecase.ShouldShowDeleteAllDialog
import com.pierre.shortner.feature.links.top_bar.presentation.factory.DeleteAllUiActionFactory
import com.pierre.shortner.feature.links.top_bar.presentation.model.ShortenerTopBarUiAction
import com.pierre.shortner.model.routes.links.delete.DeleteAllLinksRoute
import shortener.feature.links.top_bar.presentation.generated.resources.Res
import shortener.feature.links.top_bar.presentation.generated.resources.there_are_no_links_to_delete

class DeleteAllUiActionFactoryImpl(
    private val shouldShowDeleteAllDialog: ShouldShowDeleteAllDialog,
): DeleteAllUiActionFactory {
    override suspend fun create(): ShortenerTopBarUiAction = if (shouldShowDeleteAllDialog()) {
        ShortenerTopBarUiAction.NavigateToRoute(DeleteAllLinksRoute)
    } else {
        ShortenerTopBarUiAction.ShowSnackbar(Res.string.there_are_no_links_to_delete)
    }
}
