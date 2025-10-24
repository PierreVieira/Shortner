package com.pierre.shortner.feature.links.top_bar.mapper

import com.pierre.shortner.feature.links.top_bar.model.ShortenerTopBarUiEvent

fun interface ShortenerTopBarUiEventMapper {
    fun map(event: ShortenerTopBarUiEvent): Any
}
