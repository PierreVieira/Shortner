package com.pierre.shortner.feature.links.content.presentation.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.test.longClick
import com.pierre.shortner.feature.links.content.presentation.model.LinkPresentationModel
import com.pierre.shortner.feature.links.content.presentation.model.event.LinksUiEvent
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalTestApi::class)
class LinkCardContentTest {

    @Test
    fun `Given a link model When LinkCardContent is composed Then shows shortened url alias and createdAt`() =
        runComposeUiTest {
            // Given
            val model = LinkPresentationModel(
                id = 42L,
                originalUrl = "https://example.com/original",
                shortenedUrl = "https://sho.rt/abc",
                alias = "my-alias",
                createdAt = "2025-10-25",
                isCardExpanded = false,
                isMenuExpanded = false,
            )
            val events = mutableStateListOf<LinksUiEvent>()

            setContent {
                LinkCardContent(
                    linkPresentationModel = model,
                    onEvent = { events.add(it) },
                )
            }

            // Then
            onNodeWithText(model.shortenedUrl).assertIsDisplayed()
            onNodeWithText(model.alias).assertIsDisplayed()
            onNodeWithText(model.createdAt).assertIsDisplayed()
        }

    @Test
    fun `Given a link model When clicking and long-pressing shortened url Then emits corresponding ui events`() =
        runComposeUiTest {
            // Given
            val model = LinkPresentationModel(
                id = 7L,
                originalUrl = "https://example.com/long",
                shortenedUrl = "https://sho.rt/xyz",
                alias = "alias-xyz",
                createdAt = "2025-10-25",
                isCardExpanded = false,
                isMenuExpanded = false,
            )
            val events = mutableListOf<LinksUiEvent>()

            setContent {
                MaterialTheme {
                    LinkCardContent(
                        linkPresentationModel = model,
                        onEvent = { events.add(it) },
                    )
                }
            }

            // When
            onNodeWithText(model.shortenedUrl).performClick()
            onNodeWithText(
                model.shortenedUrl,
            ).performTouchInput { longClick() }

            // Then
            assertEquals(
                listOf(
                    LinksUiEvent.OnShortenedLinkClick(model.id),
                    LinksUiEvent.OnShortenedLinkLongPress(model.id),
                ),
                events,
            )
        }
}
