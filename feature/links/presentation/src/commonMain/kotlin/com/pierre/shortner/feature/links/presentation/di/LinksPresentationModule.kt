package com.pierre.shortner.feature.links.presentation.di

import com.pierre.shortner.feature.links.presentation.LinksViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val linksPresentationModule = module {
    viewModelOf(::LinksViewModel)
}
