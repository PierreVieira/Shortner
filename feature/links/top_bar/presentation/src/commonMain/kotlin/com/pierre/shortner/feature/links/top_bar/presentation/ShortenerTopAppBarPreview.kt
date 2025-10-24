package com.pierre.shortner.feature.links.top_bar.presentation

import androidx.compose.runtime.Composable
import com.pierre.shortner.feature.links.top_bar.presentation.factory.impl.ShortenerTopBarButtonsFactoryImpl
import com.pierre.shortner.model.theme.Theme
import com.pierre.shortner.ui.theme.preview.PreviewTheme
import com.pierre.shortner.ui.theme.preview.ThemePreviewParameterProvider
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

private val factory = ShortenerTopBarButtonsFactoryImpl()

@Preview
@Composable
private fun ShortenerTopAppBarPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class)
    currentTheme: Theme,
) {
    PreviewTheme(currentTheme) {
        ShortenerTopAppBar(
            buttons = factory.create(),
            onEvent = {}
        )
    }
}
