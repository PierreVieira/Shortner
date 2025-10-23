package com.pierre.shortner.feature.theme_selection.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.pierre.shortner.model.routes.theme.ThemeSettingsRoute
import com.pierre.shortner.ui.utils.ActionCollector
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.themeSettings(
    navController: NavController,
    switchPlatformColorSchemeComponent: @Composable (Modifier) -> Unit,
) {
    composable<ThemeSettingsRoute> {
        val viewModel = koinViewModel<ThemeSelectionViewModel>()
        ActionCollector(viewModel.navigateBackUiAction) {
            navController.navigateUp()
        }
        ThemeSelectionScreen(
            options = viewModel.themeOptions,
            onEvent = viewModel::onEvent,
            switchPlatformColorSchemeComponent = switchPlatformColorSchemeComponent,
        )
    }
}
