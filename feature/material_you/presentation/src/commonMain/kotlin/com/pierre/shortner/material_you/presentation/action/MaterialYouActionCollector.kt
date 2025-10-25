package com.pierre.shortner.material_you.presentation.action

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.pierre.shortner.material_you.presentation.model.AndroidColorSchemeUiAction
import com.pierre.shortner.model.routes.theme.MaterialYouRoute
import com.pierre.shortner.ui.utils.ActionCollector
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaterialYouActionCollector(
    flow: Flow<AndroidColorSchemeUiAction>,
    navHostController: NavHostController,
    materialYouBottomSheetState: SheetState
) {
    ActionCollector(flow) { uiAction ->
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
