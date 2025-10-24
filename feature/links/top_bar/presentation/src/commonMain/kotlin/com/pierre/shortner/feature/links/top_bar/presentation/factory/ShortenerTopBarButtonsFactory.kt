package com.pierre.shortner.feature.links.top_bar.presentation.factory

import com.pierre.shortner.feature.links.top_bar.presentation.model.ShortenerTopBarButtonModel

fun interface ShortenerTopBarButtonsFactory {
    fun create(): List<ShortenerTopBarButtonModel>
}
