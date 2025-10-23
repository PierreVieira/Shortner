package com.pierre.shortner.material_you.domain.usecase

interface SetIsDynamicColorsEnabled {
    suspend operator fun invoke(isEnabled: Boolean)
}
