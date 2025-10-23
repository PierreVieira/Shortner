package com.pierre.shortner.ui.theme.preview

import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider
import com.pierre.shortner.model.theme.Theme

class ThemePreviewParameterProvider : PreviewParameterProvider<Theme> {
    override val values: Sequence<Theme> = sequenceOf(
        Theme.LIGHT,
        Theme.DARK,
    )
}
