package com.pierre.shortner.feature.theme_selection.domain.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import com.pierre.shortner.feature.theme_selection.domain.usecase.GetThemeOptionFlow
import com.pierre.shortner.feature.theme_selection.domain.usecase.SetThemeOption
import com.pierre.shortner.feature.theme_selection.domain.usecase.impl.GetThemeOptionFlowUseCase
import com.pierre.shortner.feature.theme_selection.domain.usecase.impl.SetThemeOptionUseCase

val themeSelectionDomainModule = module {
    factoryOf(::GetThemeOptionFlowUseCase).bind<GetThemeOptionFlow>()
    factoryOf(::SetThemeOptionUseCase).bind<SetThemeOption>()
}
