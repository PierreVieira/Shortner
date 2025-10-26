package com.pierre.shortner.ui.components.icon_button

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalTestApi::class)
class ArrowBackIconButtonTest {

    @Test
    fun `Given ArrowBackIconButton When composed Then it shows icon with Back contentDescription and is clickable`() =
        runComposeUiTest {
            // Given
            setContent {
                ArrowBackIconButton(onClick = {})
            }

            // Then
            onNodeWithContentDescription("Back to the previous screen")
                .assertIsDisplayed()
                .assertHasClickAction()
        }

    @Test
    fun `Given ArrowBackIconButton When clicked Then it invokes onClick`() = runComposeUiTest {
        // Given
        var clicks = 0
        setContent {
            ArrowBackIconButton(onClick = { clicks++ })
        }

        // When
        onNodeWithContentDescription("Back to the previous screen").performClick()

        // Then
        assertEquals(1, clicks)
    }
}
