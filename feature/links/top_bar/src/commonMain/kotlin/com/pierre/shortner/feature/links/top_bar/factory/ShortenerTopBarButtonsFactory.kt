package com.pierre.shortner.feature.links.top_bar.factory

import com.pierre.shortner.feature.links.top_bar.model.ShortenerTopBarButtonModel

fun interface ShortenerTopBarButtonsFactory {
    fun create(): List<ShortenerTopBarButtonModel>
}
