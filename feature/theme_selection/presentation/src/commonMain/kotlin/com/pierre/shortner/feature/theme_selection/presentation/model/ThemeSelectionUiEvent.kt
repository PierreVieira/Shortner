package com.pierre.shortner.feature.theme_selection.presentation.model

import com.pierre.shortner.model.theme.Theme

sealed interface ThemeSelectionUiEvent {
    data object OnBackClick : ThemeSelectionUiEvent
    data class OnThemeSelected(val theme: Theme) : ThemeSelectionUiEvent
}
