package com.pierre.shortner.feature.links.top_bar.domain.di

import com.pierre.shortner.feature.links.top_bar.domain.usecase.ShouldShowDeleteAllDialog
import com.pierre.shortner.feature.links.top_bar.domain.usecase.ShouldShowDeleteAllDialogUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val shortenerTopBarDomainModule = module {
    factoryOf(::ShouldShowDeleteAllDialogUseCase).bind<ShouldShowDeleteAllDialog>()
}
