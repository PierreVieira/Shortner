package com.pierre.shortner.material_you.presentation.model

sealed interface AndroidColorSchemeUiAction {
    data object NavigateToMaterialYouBottomSheet : AndroidColorSchemeUiAction
    data object CloseBottomSheet : AndroidColorSchemeUiAction
}
