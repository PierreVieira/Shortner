package com.pierre.shortner.feature.links.content.presentation.model.event

sealed interface LinksUiEvent {
    data class OnDeleteLink(val id: Long) : LinksUiEvent
    data class OnToggleCardCollapse(val linkId: Long) : LinksUiEvent
    data class OnOriginalLinkClick(val id: Long) : LinksUiEvent
    data class OnOriginalLinkLongPress(val id: Long) : LinksUiEvent
    data class OnShortenedLinkClick(val id: Long) : LinksUiEvent
    data class OnShortenedLinkLongPress(val id: Long) : LinksUiEvent
}
