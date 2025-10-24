package com.pierre.shortner.feature.links.presentation.model.state

import com.pierre.shortner.feature.links.domain.model.Link

data class LinksUiState(
    val links: List<Link>,
    val isLoading: Boolean,
    val urlText: String,
    val expandedMenuLinkId: Long? = null,
)
