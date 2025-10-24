package com.pierre.shortner.feature.links.presentation.model.event

sealed interface LinksUiEvent {
    data class OnDeleteLink(val id: Long) : LinksUiEvent
    data class OnCopyLink(val id: Long) : LinksUiEvent
    data class OnMenuClick(val linkId: Long) : LinksUiEvent
    data object OnMenuDismiss : LinksUiEvent
    data class OnToggleCardCollapse(val linkId: Long) : LinksUiEvent
}
