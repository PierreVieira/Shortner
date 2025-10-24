package com.pierre.shortner.feature.links.top_bar.presentation.mapper

import com.pierre.shortner.feature.links.top_bar.presentation.model.ShortenerTopBarUiAction
import com.pierre.shortner.feature.links.top_bar.presentation.model.ShortenerTopBarUiEvent

fun interface ShortenerTopBarUiEventToUiActionMapper {
    suspend fun map(event: ShortenerTopBarUiEvent): ShortenerTopBarUiAction
}
