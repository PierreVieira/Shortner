package com.pierre.shortener

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.NavHostController
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.pierre.shortner.material_you.presentation.component.MaterialYouBottomSheet
import com.pierre.shortner.material_you.presentation.component.MaterialYouSwitchComponent
import com.pierre.shortner.material_you.presentation.model.AndroidColorSchemeUiAction
import com.pierre.shortner.material_you.presentation.viewmodel.AndroidColorSchemeViewModel
import com.pierre.shortner.model.routes.theme.MaterialYouRoute
import com.pierre.shortner.ui.utils.ActionCollector
import org.koin.androidx.viewmodel.ext.android.viewModel

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    private val androidColorSchemeViewModel: AndroidColorSchemeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isDynamicColorsOn by androidColorSchemeViewModel.isDynamicColorsOn.collectAsState()
            val onEvent = androidColorSchemeViewModel::onEvent
            val navHostController = rememberNavController()
            val materialYouBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
            MaterialYouActionCollector(navHostController, materialYouBottomSheetState)
            App(
                navHostController = navHostController,
                getSpecificColors = { isAppInDarkTheme ->
                    enableEdgeToEdge(statusBarStyle = getStatusBarStyle(isAppInDarkTheme))
                    getAndroidSpecificColorScheme(
                        isDynamicColorsOn = isDynamicColorsOn,
                        isAppInDarkTheme = isAppInDarkTheme
                    )
                },
                switchPlatformColorSchemeComponent = {
                    MaterialYouSwitchComponent(
                        isChecked = isDynamicColorsOn,
                        onEvent = onEvent
                    )
                },
                extraRoute = { navGraphBuilder ->
                    navGraphBuilder.dialog<MaterialYouRoute> {
                        MaterialYouBottomSheet(
                            sheetState = materialYouBottomSheetState,
                            isMaterialYouActivated = isDynamicColorsOn,
                            onEvent = onEvent
                        )
                    }
                }
            )
        }
    }

    @Composable
    private fun MaterialYouActionCollector(
        navHostController: NavHostController,
        materialYouBottomSheetState: SheetState
    ) {
        ActionCollector(androidColorSchemeViewModel.uiAction) { uiAction ->
            when (uiAction) {
                AndroidColorSchemeUiAction.CloseBottomSheet -> {
                    if (materialYouBottomSheetState.isVisible) {
                        materialYouBottomSheetState.hide()
                    }
                    navHostController.navigateUp()
                }
                AndroidColorSchemeUiAction.NavigateToMaterialYouBottomSheet ->
                    navHostController.navigate(MaterialYouRoute)
            }
        }
    }

    @Composable
    private fun getStatusBarStyle(isAppInDarkTheme: Boolean): SystemBarStyle = SystemBarStyle.run {
        val color = Color.Transparent.toArgb()
        if (isAppInDarkTheme) {
            dark(color)
        } else {
            light(color, color)
        }
    }
}
