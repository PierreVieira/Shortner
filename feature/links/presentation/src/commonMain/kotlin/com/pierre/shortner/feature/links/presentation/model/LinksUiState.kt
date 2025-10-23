package com.pierre.shortner.feature.links.presentation.model

import com.pierre.shortner.feature.links.domain.model.Link

data class LinksUiState(
    val links: List<Link> = emptyList(),
    val isLoading: Boolean = false,
    val urlText: String = "",
    val showDeleteConfirmation: Boolean = false,
)
