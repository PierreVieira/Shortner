package com.pierre.shortner.feature.links.domain.di

import com.pierre.shortner.feature.links.domain.repository.LinksRepository
import com.pierre.shortner.feature.links.domain.usecase.DeleteAllLinksUseCase
import com.pierre.shortner.feature.links.domain.usecase.DeleteLinkUseCase
import com.pierre.shortner.feature.links.domain.usecase.GetAllLinksUseCase
import com.pierre.shortner.feature.links.domain.usecase.ShortenUrlUseCase
import com.pierre.shortner.feature.links.domain.usecase.ValidateUrlUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val linksDomainModule = module {
    factoryOf(::ShortenUrlUseCase)
    factoryOf(::GetAllLinksUseCase)
    factoryOf(::DeleteLinkUseCase)
    factoryOf(::DeleteAllLinksUseCase)
    factoryOf(::ValidateUrlUseCase)
}
