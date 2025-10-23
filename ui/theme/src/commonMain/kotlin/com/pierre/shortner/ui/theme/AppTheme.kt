package com.pierre.shortner.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.pierre.shortner.model.theme.Theme
import com.pierre.shortner.ui.theme.colors.darkScheme
import com.pierre.shortner.ui.theme.colors.lightScheme
import com.pierre.shortner.ui.theme.utils.LocalThemeOption

@Composable
fun AppTheme(
    getSpecificColors: @Composable ((Boolean) -> ColorScheme?)? = null,
    content: @Composable () -> Unit,
) {
    val isDarkTheme = isAppInDarkTheme()
    MaterialTheme(
        colorScheme = getSpecificColors?.invoke(isDarkTheme) ?: getColorScheme(isDarkTheme),
        content = content
    )
}

@Composable
fun isAppInDarkTheme(): Boolean = when (LocalThemeOption.current) {
    Theme.LIGHT -> false
    Theme.DARK -> true
    Theme.SYSTEM -> isSystemInDarkTheme()
}

@Composable
private fun getColorScheme(isDarkTheme: Boolean): ColorScheme = if (isDarkTheme) {
    darkScheme
} else {
    lightScheme
}
