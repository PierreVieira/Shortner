package com.pierre.shortner.feature.links.input.domain.di

import com.pierre.shortner.feature.links.input.domain.usecase.ShortenUrlUseCase
import com.pierre.shortner.feature.links.input.domain.usecase.ValidateUrlUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val inputDomainModule = module {
    factoryOf(::ShortenUrlUseCase)
    factoryOf(::ValidateUrlUseCase)
}
