package com.pierre.shortner.core.data_store_provider.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import com.pierre.shortner.core.data_store_provider.createDataStore

val dataStoreProviderModule = module { singleOf(::createDataStore) }
