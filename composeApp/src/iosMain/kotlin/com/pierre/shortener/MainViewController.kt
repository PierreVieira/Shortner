package com.pierre.shortener

import androidx.compose.ui.window.ComposeUIViewController
import com.pierre.shortener.di.initializeKoin
import com.pierre.shortner.core.room_provider.db.getDatabaseBuilder
import org.koin.dsl.module
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController(
    configure = {
        initializeKoin(
            platformModules = listOf(
                module {
                    single {
                        getDatabaseBuilder()
                    }
                }
            )
        )
    },
) {
    App()
}
