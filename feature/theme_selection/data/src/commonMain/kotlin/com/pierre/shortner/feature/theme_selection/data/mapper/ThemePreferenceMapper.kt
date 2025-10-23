package com.pierre.shortner.feature.theme_selection.data.mapper

import com.pierre.shortner.model.theme.Theme

interface ThemePreferenceMapper {
    fun mapPreferenceToModel(preference: String?): Theme
    fun mapModelToPreference(theme: Theme): String
}
