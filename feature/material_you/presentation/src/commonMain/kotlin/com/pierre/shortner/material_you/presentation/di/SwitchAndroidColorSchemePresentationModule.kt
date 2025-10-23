package com.pierre.shortner.material_you.presentation.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import com.pierre.shortner.material_you.presentation.viewmodel.AndroidColorSchemeViewModel

val materialYouPresentationModule = module {
    viewModelOf(::AndroidColorSchemeViewModel)
}
