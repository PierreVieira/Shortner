package com.pierre.shortner.ui.components.shimmer.shimmer

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.takeOrElse
import com.pierre.shortner.ui.components.shimmer.GradientShimmerModel
import com.pierre.shortner.ui.components.shimmer.placeholder.PlaceholderHighlight
import com.pierre.shortner.ui.components.shimmer.placeholder.placeholder
import com.pierre.shortner.ui.components.shimmer.placeholder.shimmer

fun Modifier.shimmer(
    isLoading: Boolean,
    color: Color = Color.Unspecified,
    shape: Shape = RectangleShape,
    showShimmerAnimation: Boolean = true,
    gradientShimmerModel: GradientShimmerModel? = null,
    shimmerAlphaMultiplier: Float = 1f,
): Modifier = composed {
    val defaultColor = MaterialTheme.colorScheme.onTertiary
    val highlight = if (showShimmerAnimation) {
        PlaceholderHighlight.shimmer(
            highlightColors = gradientShimmerModel?.let {
                listOf(
                    it.startColor.copy(alpha = 0f),
                    it.startColor.copy(alpha = it.startColor.alpha * shimmerAlphaMultiplier),
                    it.endColor.copy(alpha = it.endColor.alpha * shimmerAlphaMultiplier),
                    it.endColor.copy(alpha = 0f),
                )
            } ?: listOf(
                defaultColor.copy(alpha = 0f),
                defaultColor,
                defaultColor.copy(alpha = 0f),
            )
        )
    } else {
        null
    }
    val specifiedBackgroundColor = color.takeOrElse {
        Color(0xFFDBD6D1).copy(0.6f)
    }
    Modifier.placeholder(
        color = specifiedBackgroundColor,
        visible = isLoading,
        shape = shape,
        highlight = highlight
    )
}
