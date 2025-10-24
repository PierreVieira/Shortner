package com.pierre.shortner.feature.links.delete_all.presentation.di

import com.pierre.shortner.feature.links.delete_all.presentation.viewmodel.DeleteAllLinksViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val deleteAllLinksPresentationModule = module {
    viewModelOf(::DeleteAllLinksViewModel)
}
