package com.pierre.shortner.feature.theme_selection.domain.usecase.impl

import com.pierre.shortner.model.theme.Theme
import com.pierre.shortner.feature.theme_selection.domain.repository.ThemeSelectionRepository
import com.pierre.shortner.feature.theme_selection.domain.usecase.SetThemeOption

internal class SetThemeOptionUseCase(
    private val repository: ThemeSelectionRepository,
): SetThemeOption {

    override suspend operator fun invoke(theme: Theme) {
        repository.setTheme(theme)
    }
}
