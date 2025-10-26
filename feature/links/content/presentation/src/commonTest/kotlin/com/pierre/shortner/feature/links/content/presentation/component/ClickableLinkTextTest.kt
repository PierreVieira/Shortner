package com.pierre.shortner.feature.links.content.presentation.component

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.longClick
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.runComposeUiTest
import com.pierre.shortner.feature.links.content.presentation.model.event.LinksUiEvent
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalTestApi::class)
class ClickableLinkTextTest {

    @Test
    fun `Given a link text When ClickableLinkText is composed Then it is displayed`() = runComposeUiTest {
        // Given
        val text = "https://sho.rt/abc"
        val events = mutableListOf<LinksUiEvent>()

        setContent {
            ClickableLinkText(
                text = text,
                linkId = 1L,
                onEvent = { events.add(it) },
                onClickEvent = LinksUiEvent.OnShortenedLinkClick(1L),
                onLongPressEvent = LinksUiEvent.OnShortenedLinkLongPress(1L),
            )
        }

        // Then
        onNodeWithText(text).assertIsDisplayed()
    }

    @Test
    fun `Given a link text When clicked Then emits OnShortenedLinkClick`() = runComposeUiTest {
        // Given
        val linkId = 42L
        val text = "https://sho.rt/xyz"
        val events = mutableListOf<LinksUiEvent>()

        setContent {
            ClickableLinkText(
                text = text,
                linkId = linkId,
                onEvent = { events.add(it) },
                onClickEvent = LinksUiEvent.OnShortenedLinkClick(linkId),
                onLongPressEvent = LinksUiEvent.OnShortenedLinkLongPress(linkId),
            )
        }

        // When
        onNodeWithText(text).performClick()

        // Then
        assertEquals(listOf<LinksUiEvent>(LinksUiEvent.OnShortenedLinkClick(linkId)), events)
    }

    @Test
    fun `Given a link text When long pressed Then emits OnShortenedLinkLongPress`() = runComposeUiTest {
        // Given
        val linkId = 7L
        val text = "https://sho.rt/long"
        val events = mutableListOf<LinksUiEvent>()

        setContent {
            ClickableLinkText(
                text = text,
                linkId = linkId,
                onEvent = { events.add(it) },
                onClickEvent = LinksUiEvent.OnShortenedLinkClick(linkId),
                onLongPressEvent = LinksUiEvent.OnShortenedLinkLongPress(linkId),
            )
        }

        // When
        onNodeWithText(text).performTouchInput { longClick() }

        // Then
        assertEquals(listOf<LinksUiEvent>(LinksUiEvent.OnShortenedLinkLongPress(linkId)), events)
    }
}
