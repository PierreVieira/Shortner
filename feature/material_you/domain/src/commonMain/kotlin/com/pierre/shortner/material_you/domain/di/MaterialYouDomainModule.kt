package com.pierre.shortner.material_you.domain.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import com.pierre.shortner.material_you.domain.model.MaterialYouUseCases
import com.pierre.shortner.material_you.domain.usecase.GetIsDynamicColorsEnabledFlow
import com.pierre.shortner.material_you.domain.usecase.SetIsDynamicColorsEnabled
import com.pierre.shortner.material_you.domain.usecase.impl.GetIsDynamicColorsEnabledFlowUseCase
import com.pierre.shortner.material_you.domain.usecase.impl.SetIsDynamicColorsEnabledUseCase

val materialYouDomainModule = module {
    factoryOf(::MaterialYouUseCases)
    factoryOf(::GetIsDynamicColorsEnabledFlowUseCase).bind<GetIsDynamicColorsEnabledFlow>()
    factoryOf(::SetIsDynamicColorsEnabledUseCase).bind<SetIsDynamicColorsEnabled>()
}
