package com.pierre.shortner.network.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import com.pierre.shortner.network.data.handler.RequestHandler
import com.pierre.shortner.network.utils.getHttpClient

val networkModule = module {
    singleOf(::getHttpClient)
    factoryOf(::RequestHandler)
}
