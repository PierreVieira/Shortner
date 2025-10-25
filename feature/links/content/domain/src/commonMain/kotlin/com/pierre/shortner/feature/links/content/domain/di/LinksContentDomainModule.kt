package com.pierre.shortner.feature.links.content.domain.di

import com.pierre.shortner.feature.links.content.domain.usecase.GetAllLinksUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val linksContentDomainModule = module {
    factoryOf(::GetAllLinksUseCase)
}
