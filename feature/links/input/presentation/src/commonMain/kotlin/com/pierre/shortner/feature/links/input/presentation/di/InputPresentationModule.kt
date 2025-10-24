package com.pierre.shortner.feature.links.input.presentation.di

import com.pierre.shortner.feature.links.input.presentation.viewmodel.LinkInputViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val inputPresentationModule = module {
    viewModelOf(::LinkInputViewModel)
}
