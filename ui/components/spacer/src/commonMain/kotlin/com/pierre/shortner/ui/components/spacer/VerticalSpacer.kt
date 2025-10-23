package com.pierre.shortner.ui.components.spacer

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A vertical spacer with a customizable size in [Dp].
 *
 * Adds empty vertical space in layouts such as [Column], helping with consistent spacing.
 *
 * Example usage:
 * ```kotlin
 * @Composable
 * fun ExampleVerticalSpacerWithDp() {
 *     Column {
 *         Text("Title")
 *         VerticalSpacer(size = 16.dp)
 *         Text("Subtitle")
 *     }
 * }
 * ```
 *
 * @param size The height of the spacer. Defaults to `16.dp` if not specified.
 */
@Composable
fun VerticalSpacer(size: Dp = 16.dp) {
    BaseSpacer(
        orientation = Orientation.Vertical,
        size = size,
    )
}

/**
 * A vertical spacer that takes the size as an [Int] and converts it to [Dp].
 *
 * Useful for cases where spacing is defined in raw pixel-independent units.
 *
 * Example usage:
 *
 * ```kotlin
 * @Composable
 * fun ExampleVerticalSpacerWithInt() {
 *     Column {
 *         Text("Option 1")
 *         VerticalSpacer(size = 20)
 *         Text("Option 2")
 *     }
 * }
 * ```
 *
 * @param size The height of the spacer in raw dp units.
 */
@Composable
fun VerticalSpacer(size: Int) {
    VerticalSpacer(size.dp)
}
