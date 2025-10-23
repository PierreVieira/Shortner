package com.pierre.shortner.material_you.presentation.model

sealed interface AndroidColorSchemeUiEvent {
    data class OnIsDynamicColorsEnabledChange(val isEnabled: Boolean) : AndroidColorSchemeUiEvent
    data object OnInformationIconClick : AndroidColorSchemeUiEvent
    data object OnInformationDialogDismiss : AndroidColorSchemeUiEvent
    data object BottomSheetGotItClick: AndroidColorSchemeUiEvent
}
