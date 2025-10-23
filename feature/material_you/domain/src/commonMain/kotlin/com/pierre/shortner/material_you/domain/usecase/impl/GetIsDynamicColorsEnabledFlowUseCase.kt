package com.pierre.shortner.material_you.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import com.pierre.shortner.material_you.domain.repository.MaterialYouRepository
import com.pierre.shortner.material_you.domain.usecase.GetIsDynamicColorsEnabledFlow

internal class GetIsDynamicColorsEnabledFlowUseCase(
    private val repository: MaterialYouRepository,
) : GetIsDynamicColorsEnabledFlow {
    override fun invoke(): Flow<Boolean> = repository.getIsDynamicColorsEnabledFlow()
}
