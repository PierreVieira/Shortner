package com.pierre.shortner.feature.links.presentation.model.state

import com.pierre.shortner.feature.links.presentation.model.LinkPresentationModel

data class LinksUiState(
    val links: List<LinkPresentationModel>,
    val isSendButtonLoading: Boolean,
    val urlText: String,
)
