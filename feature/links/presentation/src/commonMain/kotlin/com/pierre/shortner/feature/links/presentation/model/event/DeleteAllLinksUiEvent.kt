package com.pierre.shortner.feature.links.presentation.model.event

sealed interface DeleteAllLinksUiEvent {
    data object OnCancelClick : DeleteAllLinksUiEvent
    data object OnConfirmClick : DeleteAllLinksUiEvent
}
