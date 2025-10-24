package com.pierre.shortner.feature.links.top_bar.model

import org.jetbrains.compose.resources.StringResource

sealed interface ShortenerTopBarUiAction {
    data class NavigateToRoute(val route: Any): ShortenerTopBarUiAction
    data class ShowSnackbar(val stringResource: StringResource): ShortenerTopBarUiAction
}
