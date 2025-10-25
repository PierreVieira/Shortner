package com.pierre.shortner.feature.links.content.presentation.model.state

data class LinksContentUiState(
    val links: LinkListUiState,
    val isSendButtonLoading: Boolean,
    val urlText: String,
)
