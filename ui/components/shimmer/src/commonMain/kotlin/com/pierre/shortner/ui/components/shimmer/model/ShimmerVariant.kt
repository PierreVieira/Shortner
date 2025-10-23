package com.pierre.shortner.ui.components.shimmer.model

import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Describes skeleton variants for shimmer placeholders.
 */
sealed interface ShimmerVariant {
    /**
     * Generic rectangle (or any supplied [shape]).
     */
    data class Rectangle(val shape: Shape? = null) : ShimmerVariant

    /**
     * A circular placeholder with a fixed [diameter].
     */
    data class Circle(val diameter: Dp) : ShimmerVariant

    /**
     * A horizontal row of star placeholders.
     *
     * @param count How many stars to draw.
     * @param starSize The size (width/height) of each star.
     * @param spacing Horizontal spacing between stars.
     */
    data class StarsRow(
        val count: Int = 5,
        val starSize: Dp = 20.dp,
        val spacing: Dp = 8.dp,
    ) : ShimmerVariant

    data class Heart(val size: Dp = 20.dp) : ShimmerVariant
}
