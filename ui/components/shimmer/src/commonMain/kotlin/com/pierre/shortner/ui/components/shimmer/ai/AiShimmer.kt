package com.pierre.shortner.ui.components.shimmer.ai

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.pierre.shortner.ui.components.shimmer.GradientShimmerModel
import com.pierre.shortner.ui.components.shimmer.shimmer.shimmer

fun Modifier.aiShimmer(): Modifier {
    val gradientColors = GradientShimmerModel(
        startColor = Color(0xFFD8E7FF),
        endColor = Color(0xFFE7D8FF),
    )
    return shimmer(
        isLoading = true,
        gradientShimmerModel = gradientColors,
        color = gradientColors.endColor.copy(alpha = 0.6f),
    )
}
