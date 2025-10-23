package com.pierre.shortner.ui.components.shimmer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.pierre.shortner.ui.components.shimmer.ai.aiShimmer
import com.pierre.shortner.ui.components.shimmer.model.HeartShape
import com.pierre.shortner.ui.components.shimmer.model.ShimmerVariant
import com.pierre.shortner.ui.components.shimmer.model.StarShape
import com.pierre.shortner.ui.components.shimmer.shimmer.shimmer


/**
 * Versatile shimmer component that can render rectangles, circles, or a row of stars.
 *
 * @param modifier Base modifier applied to the container (or rectangle variant).
 * @param isAiShimmer Whether to use the AI shimmer variant.
 * @param variant Which skeleton to draw (rectangle by default).
 */
@Composable
fun ShimmerComponent(
    modifier: Modifier = Modifier,
    isAiShimmer: Boolean = false,
    variant: ShimmerVariant = ShimmerVariant.Rectangle(),
) {
    fun Modifier.applyChosenShimmer(): Modifier =
        if (isAiShimmer) this.aiShimmer() else this.shimmer(isLoading = true)

    when (variant) {
        is ShimmerVariant.Rectangle -> {
            val shaped = variant.shape?.let { modifier.clip(it) } ?: modifier
            Box(modifier = shaped) {
                Spacer(modifier = Modifier.fillMaxSize().applyChosenShimmer())
            }
        }

        is ShimmerVariant.Circle -> Box(
            modifier = modifier
                .size(variant.diameter)
                .clip(CircleShape)
                .applyChosenShimmer()
        )

        is ShimmerVariant.StarsRow -> Row {
            repeat(variant.count) { index ->
                Box(
                    modifier = Modifier
                        .size(variant.starSize)
                        .clip(StarShape())
                        .applyChosenShimmer()
                )
                if (index != variant.count - 1) {
                    Spacer(modifier = Modifier.width(variant.spacing))
                }
            }
        }

        is ShimmerVariant.Heart -> Box(
            modifier = modifier
                .size(variant.size)
                .clip(HeartShape())
                .applyChosenShimmer()
        )
    }
}
