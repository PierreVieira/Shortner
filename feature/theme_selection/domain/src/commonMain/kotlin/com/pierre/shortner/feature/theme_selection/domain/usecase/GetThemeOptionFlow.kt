package com.pierre.shortner.feature.theme_selection.domain.usecase

import kotlinx.coroutines.flow.Flow
import com.pierre.shortner.model.theme.Theme

fun interface GetThemeOptionFlow {
    operator fun invoke(): Flow<Theme>
}
