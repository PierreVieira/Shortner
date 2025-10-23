package com.pierre.shortner.feature.theme_selection.domain.usecase

import com.pierre.shortner.model.theme.Theme

fun interface SetThemeOption {
    suspend operator fun invoke(theme: Theme)
}
