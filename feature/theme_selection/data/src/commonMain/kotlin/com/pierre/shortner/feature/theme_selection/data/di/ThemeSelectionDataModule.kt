package com.pierre.shortner.feature.theme_selection.data.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import com.pierre.shortner.feature.theme_selection.data.mapper.ThemePreferenceMapper
import com.pierre.shortner.feature.theme_selection.data.mapper.ThemePreferenceMapperImpl
import com.pierre.shortner.feature.theme_selection.data.repository.ThemeSelectionRepositoryImpl
import com.pierre.shortner.feature.theme_selection.domain.repository.ThemeSelectionRepository

val themeSelectionDataModule = module {
    factoryOf(::ThemeSelectionRepositoryImpl).bind<ThemeSelectionRepository>()
    factoryOf(::ThemePreferenceMapperImpl).bind<ThemePreferenceMapper>()
}
