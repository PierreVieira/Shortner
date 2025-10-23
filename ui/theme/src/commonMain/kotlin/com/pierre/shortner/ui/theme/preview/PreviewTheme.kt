package com.pierre.shortner.ui.theme.preview

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.pierre.shortner.model.theme.Theme
import com.pierre.shortner.ui.theme.AppTheme
import com.pierre.shortner.ui.theme.utils.LocalThemeOption

@Composable
fun PreviewTheme(currentTheme: Theme, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalThemeOption provides currentTheme) {
        AppTheme {
            Surface(color = MaterialTheme.colorScheme.background) {
                content()
            }
        }
    }
}
