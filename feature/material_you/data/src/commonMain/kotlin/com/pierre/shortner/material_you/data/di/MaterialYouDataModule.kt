package com.pierre.shortner.material_you.data.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import com.pierre.shortner.material_you.data.repository.MaterialYouRepositoryImpl
import com.pierre.shortner.material_you.domain.repository.MaterialYouRepository

val materialYouDataModule = module {
    singleOf(::MaterialYouRepositoryImpl).bind<MaterialYouRepository>()
}
