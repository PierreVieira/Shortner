package com.pierre.shortner.feature.theme_selection.domain.repository

import kotlinx.coroutines.flow.Flow
import com.pierre.shortner.model.theme.Theme

interface ThemeSelectionRepository {
    fun getThemeFlow(): Flow<Theme>
    suspend fun setTheme(theme: Theme)
}
