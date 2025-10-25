package com.pierre.shortner.feature.links.input.domain.di

import com.pierre.shortner.feature.links.input.domain.usecase.IsLinkAlreadyAdded
import com.pierre.shortner.feature.links.input.domain.usecase.PostUrl
import com.pierre.shortner.feature.links.input.domain.usecase.ValidateUrl
import com.pierre.shortner.feature.links.input.domain.usecase.impl.IsLinkAlreadyAddedUseCase
import com.pierre.shortner.feature.links.input.domain.usecase.impl.PostUrlUseCase
import com.pierre.shortner.feature.links.input.domain.usecase.impl.ValidateUrlUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val inputDomainModule = module {
    factoryOf(::PostUrlUseCase).bind<PostUrl>()
    factoryOf(::ValidateUrlUseCase).bind<ValidateUrl>()
    factoryOf(::IsLinkAlreadyAddedUseCase).bind<IsLinkAlreadyAdded>()
}
