package com.pierre.shortener.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pierre.shortner.feature.theme_selection.domain.usecase.GetThemeOptionFlow
import com.pierre.shortner.model.theme.Theme
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class AppViewModel(getThemeOptionFlow: GetThemeOptionFlow): ViewModel() {

    val themeState: StateFlow<Theme> =
        getThemeOptionFlow()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = Theme.SYSTEM
            )
}
