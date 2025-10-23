package com.pierre.shortner.feature.theme_selection.data.mapper

import com.pierre.shortner.model.theme.Theme

internal class ThemePreferenceMapperImpl : ThemePreferenceMapper {
    override fun mapPreferenceToModel(preference: String?): Theme = when (preference) {
        LIGHT_THEME -> Theme.LIGHT
        DARK_THEME -> Theme.DARK
        else -> Theme.SYSTEM
    }

    override fun mapModelToPreference(theme: Theme): String =
        when (theme) {
            Theme.LIGHT -> LIGHT_THEME
            Theme.DARK -> DARK_THEME
            Theme.SYSTEM -> SYSTEM_THEME
        }

    companion object {
        private const val DARK_THEME = "dark_theme"
        private const val LIGHT_THEME = "light_theme"
        private const val SYSTEM_THEME = "system_theme"
    }
}
