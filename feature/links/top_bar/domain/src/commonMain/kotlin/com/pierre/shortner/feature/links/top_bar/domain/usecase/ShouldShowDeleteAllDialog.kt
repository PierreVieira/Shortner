package com.pierre.shortner.feature.links.top_bar.domain.usecase

fun interface ShouldShowDeleteAllDialog {
    suspend operator fun invoke(): Boolean
}
