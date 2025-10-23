package com.pierre.shortner.feature.theme_selection.presentation.model

import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.StringResource
import com.pierre.shortner.model.theme.Theme

data class ThemeSelectionModel(
    val title: StringResource,
    val subtitle: StringResource,
    val icon: ImageVector,
    val preference: Theme,
)
