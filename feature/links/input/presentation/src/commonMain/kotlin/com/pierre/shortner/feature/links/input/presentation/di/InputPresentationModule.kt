package com.pierre.shortner.feature.links.input.presentation.di

import com.pierre.shortner.feature.links.input.presentation.factory.ValidationErrorMessageFactory
import com.pierre.shortner.feature.links.input.presentation.factory.ValidationErrorMessageFactoryImpl
import com.pierre.shortner.feature.links.input.presentation.viewmodel.LinkInputViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val inputPresentationModule = module {
    viewModelOf(::LinkInputViewModel)
    factoryOf(::ValidationErrorMessageFactoryImpl).bind<ValidationErrorMessageFactory>()
}
