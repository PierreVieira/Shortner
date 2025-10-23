package com.pierre.shortner.material_you.domain.usecase

import kotlinx.coroutines.flow.Flow

interface GetIsDynamicColorsEnabledFlow {
    operator fun invoke(): Flow<Boolean>
}
