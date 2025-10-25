package com.pierre.shortner.feature.links.content.presentation.di

import com.pierre.shortner.feature.links.content.presentation.mapper.LinkModelMapper
import com.pierre.shortner.feature.links.content.presentation.mapper.LinkModelMapperImpl
import com.pierre.shortner.feature.links.content.presentation.viewmodel.LinksContentViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val linksContentPresentationModule = module {
    factoryOf(::LinkModelMapperImpl).bind<LinkModelMapper>()
    viewModelOf(::LinksContentViewModel)
}
