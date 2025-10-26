package com.pierre.shortner.ui.components.icon_button

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalTestApi::class)
class CommonIconButtonTest {

    @Test
    fun `Given a CommonIconButton When composed Then it shows the icon with contentDescription`() = runComposeUiTest {
        // Given
        val contentDescription = "Add item"

        setContent {
            CommonIconButton(
                imageVector = Icons.Default.Add,
                contentDescription = contentDescription,
                onClick = {},
            )
        }

        // Then
        onNodeWithContentDescription(contentDescription).assertIsDisplayed().assertHasClickAction()
    }

    @Test
    fun `Given a CommonIconButton When clicked Then executes onClick callback`() = runComposeUiTest {
        // Given
        val contentDescription = "Add"
        var clicks = 0

        setContent {
            CommonIconButton(
                imageVector = Icons.Default.Add,
                contentDescription = contentDescription,
                onClick = { clicks++ },
            )
        }

        // When
        onNodeWithContentDescription(contentDescription).performClick()

        // Then
        assertEquals(1, clicks)
    }

    @Test
    fun `Given a CommonIconButton When disabled Then it is not clickable`() = runComposeUiTest {
        // Given
        val contentDescription = "Disabled button"
        var clicked = false

        setContent {
            CommonIconButton(
                imageVector = Icons.Default.Add,
                contentDescription = contentDescription,
                tint = Color.Gray,
                isEnabled = false,
                onClick = { clicked = true },
            )
        }

        // Then
        onNodeWithContentDescription(contentDescription).assertIsDisplayed().assertIsNotEnabled()
        assertEquals(false, clicked)
    }

    @Test
    fun `Given a CommonIconButton When enabled Then it is clickable`() = runComposeUiTest {
        // Given
        val contentDescription = "Enabled button"

        setContent {
            CommonIconButton(
                imageVector = Icons.Default.Add,
                contentDescription = contentDescription,
                onClick = {},
            )
        }

        // Then
        onNodeWithContentDescription(contentDescription).assertIsDisplayed().assertIsEnabled()
    }
}
