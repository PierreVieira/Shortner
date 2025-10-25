package com.pierre.shortner.feature.links.content.presentation.model.state

import com.pierre.shortner.feature.links.content.presentation.model.LinkPresentationModel

data class LinksUiState(
    val links: List<LinkPresentationModel>,
    val isSendButtonLoading: Boolean,
    val urlText: String,
)
