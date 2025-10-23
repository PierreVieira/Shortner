package com.pierre.shortner.ui.components.shimmer.model

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

/**
 * Star shape used to clip a box into a 5-point star silhouette.
 * It scales with the composable's size.
 */
internal class StarShape : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline = Outline.Generic(buildStarPath(size))

    private fun buildStarPath(size: Size): Path {
        val path = Path()
        val dim = min(size.width, size.height)
        val cx = size.width / 2f
        val cy = size.height / 2f
        val outerR = dim / 2f
        // sin(18°) / sin(54°) for a regular pentagram
        val innerR = outerR * 0.38196601125f

        val totalPoints = 10
        val step = (2 * PI / totalPoints).toFloat()
        val startAngle = (-PI / 2).toFloat() // point up

        for (i in 0 until totalPoints) {
            val r = if (i % 2 == 0) outerR else innerR
            val angle = startAngle + i * step
            val x = cx + r * cos(angle)
            val y = cy + r * sin(angle)
            if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }
        path.close()
        return path
    }

    companion object {
        private const val PI = kotlin.math.PI
    }
}
