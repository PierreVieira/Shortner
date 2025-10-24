package com.pierre.shortner.feature.links.top_bar.presentation.factory.impl

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Palette
import com.pierre.shortner.feature.links.top_bar.presentation.factory.ShortenerTopBarButtonsFactory
import com.pierre.shortner.feature.links.top_bar.presentation.model.ShortenerTopBarButtonModel
import com.pierre.shortner.feature.links.top_bar.presentation.model.ShortenerTopBarUiEvent
import shortener.feature.links.top_bar.presentation.generated.resources.Res
import shortener.feature.links.top_bar.presentation.generated.resources.change_theme_button_content_description
import shortener.feature.links.top_bar.presentation.generated.resources.delete_all_button_content_description

class ShortenerTopBarButtonsFactoryImpl : ShortenerTopBarButtonsFactory {
    override fun create(): List<ShortenerTopBarButtonModel> = listOf(
        ShortenerTopBarButtonModel(
            contentDescriptionResource = Res.string.delete_all_button_content_description,
            uiEvent = ShortenerTopBarUiEvent.ON_DELETE_ALL_CLICK,
            imageVector = Icons.Default.Delete,
        ),
        ShortenerTopBarButtonModel(
            contentDescriptionResource = Res.string.change_theme_button_content_description,
            uiEvent = ShortenerTopBarUiEvent.ON_THEME_CHANGE_CLICK,
            imageVector = Icons.Default.Palette,
        )
    )
}
