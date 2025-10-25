package com.pierre.shortner.feature.links.content.presentation.di

import com.pierre.shortner.feature.links.content.presentation.viewmodel.LinksContentViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val linksContentPresentationModule = module {
    viewModelOf(::LinksContentViewModel)
}
