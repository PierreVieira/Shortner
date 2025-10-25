package com.pierre.shortner.core.koin_initializer.di

import com.pierre.shortner.core.data_store_provider.di.dataStoreProviderModule
import com.pierre.shortner.core.room_provider.di.roomModule
import com.pierre.shortner.core.utils.di.utilsModule
import com.pierre.shortner.feature.links.content.data.di.linksContentDataModule
import com.pierre.shortner.feature.links.content.domain.di.linksContentDomainModule
import com.pierre.shortner.feature.links.delete_all.di.deleteAllLinksDataModule
import com.pierre.shortner.feature.links.delete_all.presentation.di.deleteAllLinksPresentationModule
import com.pierre.shortner.feature.links.input.data.di.inputDataModule
import com.pierre.shortner.feature.links.input.domain.di.inputDomainModule
import com.pierre.shortner.feature.links.input.presentation.di.inputPresentationModule
import com.pierre.shortner.feature.links.content.presentation.di.linksContentPresentationModule
import com.pierre.shortner.feature.links.delete_link.data.di.deleteLinkDataModule
import com.pierre.shortner.feature.links.delete_link.domain.di.deleteLinkDomainModule
import com.pierre.shortner.feature.links.delete_link.presentation.di.deleteLinkPresentationModule
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
            utilsModule,
        )
        val featureModules = listOf(
            deleteLinkDataModule,
            deleteLinkDomainModule,
            deleteLinkPresentationModule,
            deleteAllLinksDataModule,
            deleteAllLinksPresentationModule,
            linksContentDataModule,
            linksContentDomainModule,
            linksContentPresentationModule,
            inputDataModule,
            inputDomainModule,
            inputPresentationModule,
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
