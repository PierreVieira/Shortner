package com.pierre.shortner.feature.links.presentation.model

sealed interface LinksUiEvent {
    data object OnThemeClick : LinksUiEvent
    data object OnDeleteAllClick : LinksUiEvent
    data object OnDeleteAllConfirm : LinksUiEvent
    data object OnDeleteAllCancel : LinksUiEvent
    data class OnShortenUrl(val url: String) : LinksUiEvent
    data class OnDeleteLink(val id: Long) : LinksUiEvent
    data class OnUrlTextChange(val text: String) : LinksUiEvent
}
