package com.pierre.shortner.material_you.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import com.pierre.shortner.core.utils.updateValue
import com.pierre.shortner.material_you.domain.model.MaterialYouUseCases
import com.pierre.shortner.material_you.presentation.model.AndroidColorSchemeUiAction
import com.pierre.shortner.material_you.presentation.model.AndroidColorSchemeUiEvent
import com.pierre.shortner.ui.utils.observe

class AndroidColorSchemeViewModel(
    private val useCases: MaterialYouUseCases,
) : ViewModel() {

    private val _uiAction: Channel<AndroidColorSchemeUiAction> = Channel()
    val uiAction = _uiAction.receiveAsFlow()

    private val _uiState = MutableStateFlow(false)
    val isDynamicColorsOn: StateFlow<Boolean> = _uiState.asStateFlow()

    init {
        observe(
            flow = useCases.getIsDynamicColorsEnabledFlow(),
            collector = _uiState::updateValue,
        )
    }

    fun onEvent(event: AndroidColorSchemeUiEvent) {
        viewModelScope.launch {
            when (event) {
                is AndroidColorSchemeUiEvent.OnIsDynamicColorsEnabledChange ->
                    useCases.setIsDynamicColorsEnabled(event.isEnabled)

                AndroidColorSchemeUiEvent.OnInformationIconClick ->
                    _uiAction.send(AndroidColorSchemeUiAction.NavigateToMaterialYouBottomSheet)

                AndroidColorSchemeUiEvent.OnInformationDialogDismiss,
                AndroidColorSchemeUiEvent.BottomSheetGotItClick,
                    -> _uiAction.send(AndroidColorSchemeUiAction.CloseBottomSheet)
            }
        }
    }
}
