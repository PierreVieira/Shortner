package com.pierre.shortner.feature.links.input.presentation.model

sealed interface LinkInputUiEvent {
    data class OnUrlTextChange(val text: String) : LinkInputUiEvent
    data object OnShortenUrlClick : LinkInputUiEvent
}
