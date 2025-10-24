package com.pierre.shortner.feature.links.presentation.model.action

import org.jetbrains.compose.resources.StringResource

sealed interface LinksUiAction {
    data class ShowSnackbar(val resourceId: StringResource) : LinksUiAction
    data class Navigate(val route: Any): LinksUiAction
}
