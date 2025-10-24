package com.pierre.shortner.feature.links.input.presentation.model

import org.jetbrains.compose.resources.StringResource

sealed interface LinksUiAction {
    data class ShowSnackbar(val resourceId: StringResource) : LinksUiAction
}
