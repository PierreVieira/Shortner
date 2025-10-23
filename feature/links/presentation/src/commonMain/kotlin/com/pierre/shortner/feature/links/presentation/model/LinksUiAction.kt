package com.pierre.shortner.feature.links.presentation.model

sealed interface LinksUiAction {
    data class ShowSnackbar(val message: String) : LinksUiAction
    data object NavigateToTheme : LinksUiAction
}
