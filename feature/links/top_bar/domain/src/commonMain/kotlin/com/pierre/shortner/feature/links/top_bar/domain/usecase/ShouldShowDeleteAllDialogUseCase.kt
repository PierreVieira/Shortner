package com.pierre.shortner.feature.links.top_bar.domain.usecase

import com.pierre.shortner.feature.links.top_bar.domain.repository.ShortenerTopBarRepository

class ShouldShowDeleteAllDialogUseCase(
    private val repository: ShortenerTopBarRepository,
): ShouldShowDeleteAllDialog {

    override suspend fun invoke(): Boolean = repository.getLinks().isNotEmpty()
}
