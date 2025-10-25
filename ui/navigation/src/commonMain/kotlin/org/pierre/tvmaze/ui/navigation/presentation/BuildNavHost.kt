package org.pierre.tvmaze.ui.navigation.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.pierre.shortner.feature.links.delete_all.presentation.deleteAll
import com.pierre.shortner.feature.links.root.links
import com.pierre.shortner.feature.theme_selection.presentation.themeSettings

fun NavGraphBuilder.buildNavHost(
    navHostController: NavHostController,
    switchPlatformColorSchemeComponent: @Composable (Modifier) -> Unit,
    getNavigationModifier: (onBack: () -> Unit) -> Modifier,
) {
    links(navHostController)
    deleteAll(navHostController)
    themeSettings(
        navController = navHostController,
        switchPlatformColorSchemeComponent = switchPlatformColorSchemeComponent
    )
}
