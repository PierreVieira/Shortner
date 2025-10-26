package com.pierre.shortner.ui.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.runComposeUiTest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class ActionCollectorTest {

    @Test
    fun `Given a flow emission When collected via ActionCollector Then the UI reflects the latest value`() = runComposeUiTest {
        // Given
        val events = MutableSharedFlow<String>(replay = 0, extraBufferCapacity = 16)

        setContent {
            var lastValue by remember { mutableStateOf("-") }

            Column {
                ActionCollector(flow = events) { value ->
                    lastValue = value
                }

                Text(
                    text = lastValue,
                    modifier = Modifier.testTag("value"),
                )
            }
        }

        // Then
        onNodeWithTag("value", useUnmergedTree = true).assertTextEquals("-")

        // When
        runOnIdle { events.tryEmit("Hello") }
        waitForIdle()

        // Then
        onNodeWithTag("value", useUnmergedTree = true).assertTextEquals("Hello")

        // When
        runOnIdle { events.tryEmit("Compose") }
        waitForIdle()

        // Then
        onNodeWithTag("value", useUnmergedTree = true).assertTextEquals("Compose")
    }

    @Test
    fun `Given rapid emissions When the handler is slow Then collectLatest ensures only the last emission wins`() = runComposeUiTest {
        // Given
        val events = MutableSharedFlow<String>(replay = 0, extraBufferCapacity = 16)

        setContent {
            var shown by remember { mutableStateOf("start") }

            Column {
                ActionCollector(flow = events) { value ->
                    delay(60)
                    shown = value
                }

                Text(
                    text = shown,
                    modifier = Modifier.testTag("value"),
                )
            }
        }

        // When
        runOnIdle {
            events.tryEmit("A")
            events.tryEmit("B")
            events.tryEmit("C")
        }

        // Then
        waitForIdle()
        waitUntil(timeoutMillis = 1_000) {
            try {
                onNodeWithTag("value", useUnmergedTree = true).assertTextEquals("C")
                true
            } catch (_: AssertionError) {
                false
            }
        }
    }
}
