package com.pierre.shortner.material_you.domain.usecase.impl

import com.pierre.shortner.material_you.domain.repository.MaterialYouRepository
import com.pierre.shortner.material_you.domain.usecase.SetIsDynamicColorsEnabled

internal class SetIsDynamicColorsEnabledUseCase(
    private val repository: MaterialYouRepository,
) : SetIsDynamicColorsEnabled {
    override suspend fun invoke(isEnabled: Boolean) {
        repository.setIsDynamicColorsEnabled(isEnabled)
    }
}
