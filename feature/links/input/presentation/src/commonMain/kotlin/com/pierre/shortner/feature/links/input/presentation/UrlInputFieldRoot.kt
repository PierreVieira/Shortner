package com.pierre.shortner.feature.links.input.presentation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.pierre.shortner.feature.links.input.presentation.component.UrlInputFieldComponent
import com.pierre.shortner.feature.links.input.presentation.model.LinksUiAction
import com.pierre.shortner.feature.links.input.presentation.viewmodel.LinkInputViewModel
import com.pierre.shortner.ui.utils.ActionCollector
import org.jetbrains.compose.resources.getString
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UrlInputFieldRoot(
    snackbarHostState: SnackbarHostState,
) {
    val viewmodel = koinViewModel<LinkInputViewModel>()
    val uiState by viewmodel.uiState.collectAsState()
    ActionCollector(viewmodel.uiActions) { uiAction ->
        when(uiAction) {
            is LinksUiAction.ShowSnackbar ->
                snackbarHostState.showSnackbar(getString(uiAction.resourceId))
        }
    }
    UrlInputFieldComponent(
        uiState = uiState,
        onEvent = viewmodel::onEvent
    )
}
