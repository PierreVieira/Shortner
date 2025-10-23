package com.pierre.shortner.ui.components.spacer

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

/**
 * A base composable that renders a spacer with the given [size] and [orientation].
 *
 * Internally delegates to a [Spacer] with either horizontal or vertical dimension set,
 * depending on the provided [orientation].
 *
 * This function is intended to be used by more specific spacer composables like
 * [HorizontalSpacer] and [VerticalSpacer].
 *
 * Example usage:
 * ```kotlin
 * @Composable
 * fun ExampleBaseSpacerUsage() {
 *     Column {
 *         // Content before
 *         BaseSpacer(orientation = Orientation.Vertical, size = 24.dp)
 *         // Content after
 *     }
 * }
 * ```
 *
 * @param orientation The orientation of the spacer (either [Orientation.Horizontal] or [Orientation.Vertical]).
 * @param size The size of the spacer in the specified direction.
 */
@Composable
internal fun BaseSpacer(
    orientation: Orientation,
    size: Dp,
) {
    Spacer(
        modifier =
            when (orientation) {
                Orientation.Vertical -> Modifier.height(size)
                Orientation.Horizontal -> Modifier.width(size)
            },
    )
}
