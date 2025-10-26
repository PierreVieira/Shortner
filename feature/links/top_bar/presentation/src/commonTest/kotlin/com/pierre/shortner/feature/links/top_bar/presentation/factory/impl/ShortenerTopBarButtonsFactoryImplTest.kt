package com.pierre.shortner.feature.links.top_bar.presentation.factory.impl

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Palette
import com.pierre.shortner.feature.links.top_bar.presentation.model.ShortenerTopBarUiEvent
import shortener.feature.links.top_bar.presentation.generated.resources.Res
import shortener.feature.links.top_bar.presentation.generated.resources.change_theme_button_content_description
import shortener.feature.links.top_bar.presentation.generated.resources.delete_all_button_content_description
import kotlin.test.Test
import kotlin.test.assertEquals

class ShortenerTopBarButtonsFactoryImplTest {

    @Test
    fun `given create when called then returns list with delete and theme buttons`() {
        // Given
        val factory = ShortenerTopBarButtonsFactoryImpl()

        // When
        val result = factory.create()

        // Then
        assertEquals(2, result.size)
        
        // Delete button
        assertEquals(Res.string.delete_all_button_content_description, result[0].contentDescriptionResource)
        assertEquals(ShortenerTopBarUiEvent.ON_DELETE_ALL_CLICK, result[0].uiEvent)
        assertEquals(Icons.Default.Delete, result[0].imageVector)
        
        // Theme button
        assertEquals(Res.string.change_theme_button_content_description, result[1].contentDescriptionResource)
        assertEquals(ShortenerTopBarUiEvent.ON_THEME_CHANGE_CLICK, result[1].uiEvent)
        assertEquals(Icons.Default.Palette, result[1].imageVector)
    }
}
