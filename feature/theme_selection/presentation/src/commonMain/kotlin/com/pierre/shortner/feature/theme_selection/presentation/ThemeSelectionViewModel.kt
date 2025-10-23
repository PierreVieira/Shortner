package com.pierre.shortner.feature.theme_selection.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pierre.shortner.feature.theme_selection.domain.usecase.SetThemeOption
import com.pierre.shortner.feature.theme_selection.presentation.factory.ThemeOptionsFactory
import com.pierre.shortner.feature.theme_selection.presentation.model.ThemeSelectionUiEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class ThemeSelectionViewModel(
    private val setThemeOption: SetThemeOption,
) : ViewModel() {

    private val _navigateBackUiAction: MutableSharedFlow<Unit> = MutableSharedFlow()
    val navigateBackUiAction = _navigateBackUiAction.asSharedFlow()

    val themeOptions = ThemeOptionsFactory.themeOptions

    fun onEvent(event: ThemeSelectionUiEvent) {
        viewModelScope.launch {
            when (event) {
                ThemeSelectionUiEvent.OnBackClick -> _navigateBackUiAction.emit(Unit)
                is ThemeSelectionUiEvent.OnThemeSelected -> setThemeOption(event.theme)
            }
        }
    }
}
