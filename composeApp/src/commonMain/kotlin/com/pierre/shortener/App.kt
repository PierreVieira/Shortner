package com.pierre.shortener

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import com.pierre.shortener.presentation.AppViewModel
import com.pierre.shortner.model.theme.Theme
import com.pierre.shortner.ui.theme.AppTheme
import com.pierre.shortner.ui.theme.utils.LocalThemeOption
import org.pierre.tvmaze.ui.navigation.presentation.RootAppNavHost

@Composable
@Preview
fun App(
    navHostController: NavHostController = rememberNavController(),
    switchPlatformColorSchemeComponent: @Composable (Modifier) -> Unit = {},
    getNavigationModifier: (onBack: () -> Unit) -> Modifier = { Modifier },
    getSpecificColors: @Composable ((isAppInDarkTheme: Boolean) -> ColorScheme?)? = null,
    extraRoute: NavGraphBuilder.() -> Unit = { },
) {
    val viewModel: AppViewModel = koinViewModel()
    val theme by viewModel.themeState.collectAsState()
    ProvideCompositionLocals(theme) {
        AppTheme(getSpecificColors = getSpecificColors) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background,
            ) {
                RootAppNavHost(
                    navHostController = navHostController,
                    getNavigationModifier = getNavigationModifier,
                    switchPlatformColorSchemeComponent = switchPlatformColorSchemeComponent,
                    extraRoute = extraRoute
                )
            }
        }
    }
}

@Composable
private fun ProvideCompositionLocals(
    theme: Theme,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalThemeOption provides theme,
        content = content,
    )
}
