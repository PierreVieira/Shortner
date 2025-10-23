package com.pierre.shortner.feature.theme_selection.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import com.pierre.shortner.model.theme.Theme
import com.pierre.shortner.feature.theme_selection.domain.repository.ThemeSelectionRepository
import com.pierre.shortner.feature.theme_selection.domain.usecase.GetThemeOptionFlow

internal class GetThemeOptionFlowUseCase(
    private val repository: ThemeSelectionRepository
): GetThemeOptionFlow {
    override operator fun invoke(): Flow<Theme> = repository.getThemeFlow()
}
