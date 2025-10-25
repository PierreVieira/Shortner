package com.pierre.shortner.feature.links.delete_link.presentation.di

import com.pierre.shortner.feature.links.delete_link.presentation.DeleteLinkViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val deleteLinkPresentationModule = module {
    viewModelOf(::DeleteLinkViewModel)
}
