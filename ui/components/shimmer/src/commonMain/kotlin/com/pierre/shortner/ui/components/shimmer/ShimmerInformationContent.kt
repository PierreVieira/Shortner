package com.pierre.shortner.ui.components.shimmer

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pierre.shortner.ui.components.shimmer.model.ShimmerVariant
import com.pierre.shortner.model.data_status.DataStatus

@Composable
fun <T> DataStatus<T>.ToContent(
    modifier: Modifier,
    variant: ShimmerVariant = ShimmerVariant.Rectangle(CircleShape),
    isAiShimmer: Boolean = false,
    content: @Composable (T) -> Unit,
) {
    when (this) {
        is DataStatus.Loaded -> content(data)
        is DataStatus.Loading -> ShimmerComponent(
            modifier = modifier,
            isAiShimmer = isAiShimmer,
            variant = variant,
        )
    }
}
