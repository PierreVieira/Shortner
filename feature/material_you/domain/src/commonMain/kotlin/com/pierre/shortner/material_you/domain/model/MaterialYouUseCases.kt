package com.pierre.shortner.material_you.domain.model

import com.pierre.shortner.material_you.domain.usecase.GetIsDynamicColorsEnabledFlow
import com.pierre.shortner.material_you.domain.usecase.SetIsDynamicColorsEnabled

data class MaterialYouUseCases(
    val getIsDynamicColorsEnabledFlow: GetIsDynamicColorsEnabledFlow,
    val setIsDynamicColorsEnabled: SetIsDynamicColorsEnabled,
)
