package com.pierre.shortner.core.koin_initializer.di

import com.pierre.shortner.core.data_store_provider.di.dataStoreProviderModule
import com.pierre.shortner.core.room_provider.di.roomModule
import com.pierre.shortner.feature.links.data.di.linksDataModule
import com.pierre.shortner.feature.links.delete_all.di.deleteAllLinksDataModule
import com.pierre.shortner.feature.links.delete_all.presentation.di.deleteAllLinksPresentationModule
import com.pierre.shortner.feature.links.domain.di.linksDomainModule
import com.pierre.shortner.feature.links.presentation.di.linksPresentationModule
import com.pierre.shortner.feature.links.top_bar.data.di.shortenerTopBarDataModule
import com.pierre.shortner.feature.links.top_bar.domain.di.shortenerTopBarDomainModule
import com.pierre.shortner.feature.theme_selection.data.di.themeSelectionDataModule
import com.pierre.shortner.feature.theme_selection.di.themeSelectionPresentationModule
import com.pierre.shortner.feature.theme_selection.domain.di.themeSelectionDomainModule
import com.pierre.shortner.feature.links.top_bar.presentation.di.shortenerTopBarPresentationModule
import com.pierre.shortner.network.di.networkModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module

fun commonKoinInitializer(
    extraModules: List<Module>,
    config: (KoinApplication.() -> Unit)? = null,
) {
    startKoin {
        config?.invoke(this)
        val coreModules = listOf(
            dataStoreProviderModule,
            roomModule,
            networkModule,
        )
        val featureModules = listOf(
            deleteAllLinksDataModule,
            deleteAllLinksPresentationModule,
            linksDataModule,
            linksDomainModule,
            linksPresentationModule,
            shortenerTopBarDataModule,
            shortenerTopBarDomainModule,
            shortenerTopBarPresentationModule,
            themeSelectionDataModule,
            themeSelectionDomainModule,
            themeSelectionPresentationModule,
        )
        modules(coreModules + featureModules + extraModules)
    }
}
