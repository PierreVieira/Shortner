package com.pierre.shortner.feature.links.content.presentation.di

import com.pierre.shortner.feature.links.content.presentation.viewmodel.LinksViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val linksContentPresentationModule = module {
    viewModelOf(::LinksViewModel)
}
