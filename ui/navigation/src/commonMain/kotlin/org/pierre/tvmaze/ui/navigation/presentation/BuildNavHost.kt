package org.pierre.tvmaze.ui.navigation.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.pierre.shortner.feature.links.presentation.links
import com.pierre.shortner.feature.theme_selection.presentation.themeSettings

fun NavGraphBuilder.buildNavHost(
    navHostController: NavHostController,
    switchPlatformColorSchemeComponent: @Composable (Modifier) -> Unit,
    getNavigationModifier: (onBack: () -> Unit) -> Modifier,
) {
    links(navController = navHostController)
    themeSettings(
        navController = navHostController,
        switchPlatformColorSchemeComponent = switchPlatformColorSchemeComponent
    )
}
