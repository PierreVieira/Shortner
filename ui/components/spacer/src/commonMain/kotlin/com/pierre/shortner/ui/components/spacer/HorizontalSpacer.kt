package com.pierre.shortner.ui.components.spacer

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A horizontal spacer with a customizable size in [Dp].
 *
 * Adds empty horizontal space in layouts such as [Row], helping with consistent spacing.
 *
 * Example usage:
 *
 * ```kotlin
 * @Composable
 * fun ExampleHorizontalSpacerWithDp() {
 *     Row {
 *         Text(text = "Label")
 *         HorizontalSpacer(size = 8.dp)
 *         Text(text = "Value")
 *     }
 * }
 * ```
 *
 * @param size The width of the spacer. Defaults to `16.dp` if not specified.
 */
@Composable
fun HorizontalSpacer(size: Dp = 16.dp) {
    BaseSpacer(
        orientation = Orientation.Horizontal,
        size = size,
    )
}

/**
 * A horizontal spacer that takes the size as an [Int] and converts it to [Dp].
 *
 * Useful for cases where spacing is defined in raw pixel-independent units.
 * Example usage:
 *
 * ```kotlin
 * @Composable
 * fun ExampleHorizontalSpacerWithInt() {
 *     Row {
 *         Text(text = "Name:")
 *         HorizontalSpacer(size = 12)
 *         Text(text = "Alex")
 *     }
 * }
 * ```
 *
 * @param size The width of the spacer in raw dp units.
 */
@Composable
fun HorizontalSpacer(size: Int) {
    HorizontalSpacer(size.dp)
}
