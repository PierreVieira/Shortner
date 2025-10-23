package com.pierre.shortner.ui.components.shimmer.model

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import kotlin.math.min

/**
 * Shape that clips content into a heart silhouette.
 * Useful for shimmer placeholders of favorite/like icons.
 */
class HeartShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline = Outline.Generic(buildHeartPath(size))

    private fun buildHeartPath(size: Size): Path {
        val path = Path()

        // keep proportions using the smaller side
        val dim = min(size.width, size.height)
        val left = (size.width - dim) / 2f
        val top = (size.height - dim) / 2f
        val w = dim
        val h = dim

        fun x(t: Float) = left + t * w
        fun y(t: Float) = top + t * h

        // Bottom tip
        path.moveTo(x(0.5f), y(0.92f))

        // Left arc up
        path.cubicTo(
            x(0.15f), y(0.80f),   // cp1
            x(0.00f), y(0.55f),   // cp2
            x(0.00f), y(0.32f)    // end
        )
        // Left lobe dome
        path.cubicTo(
            x(0.00f), y(0.18f),   // cp1
            x(0.12f), y(0.06f),   // cp2
            x(0.27f), y(0.06f)    // end
        )
        // Bridge to center
        path.cubicTo(
            x(0.38f), y(0.06f),   // cp1
            x(0.46f), y(0.12f),   // cp2
            x(0.50f), y(0.20f)    // end (center top valley)
        )
        // Right lobe dome
        path.cubicTo(
            x(0.54f), y(0.12f),   // cp1
            x(0.62f), y(0.06f),   // cp2
            x(0.73f), y(0.06f)    // end
        )
        // Right arc down to tip
        path.cubicTo(
            x(0.88f), y(0.06f),   // cp1
            x(1.00f), y(0.18f),   // cp2
            x(1.00f), y(0.32f)    // end
        )
        path.cubicTo(
            x(1.00f), y(0.55f),   // cp1
            x(0.85f), y(0.80f),   // cp2
            x(0.50f), y(0.92f)    // end (back to tip)
        )

        path.close()
        return path
    }
}
