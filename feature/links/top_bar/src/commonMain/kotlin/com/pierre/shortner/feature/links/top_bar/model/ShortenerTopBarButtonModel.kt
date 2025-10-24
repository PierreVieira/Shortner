package com.pierre.shortner.feature.links.top_bar.model

import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.StringResource

data class ShortenerTopBarButtonModel(
    val contentDescriptionResource: StringResource,
    val uiEvent: ShortenerTopBarUiEvent,
    val imageVector: ImageVector,
)
