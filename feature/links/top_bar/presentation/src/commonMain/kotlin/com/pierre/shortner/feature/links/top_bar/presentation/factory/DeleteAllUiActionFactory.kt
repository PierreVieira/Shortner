package com.pierre.shortner.feature.links.top_bar.presentation.factory

import com.pierre.shortner.feature.links.top_bar.presentation.model.ShortenerTopBarUiAction

interface DeleteAllUiActionFactory {
    suspend fun create(): ShortenerTopBarUiAction
}
