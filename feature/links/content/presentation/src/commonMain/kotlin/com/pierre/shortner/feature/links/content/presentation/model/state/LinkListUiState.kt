package com.pierre.shortner.feature.links.content.presentation.model.state

import com.pierre.shortner.feature.links.content.presentation.model.LinkPresentationModel

sealed interface LinkListUiState {
    data object Loading: LinkListUiState
    data class Loaded(val data: List<LinkPresentationModel>): LinkListUiState
}
