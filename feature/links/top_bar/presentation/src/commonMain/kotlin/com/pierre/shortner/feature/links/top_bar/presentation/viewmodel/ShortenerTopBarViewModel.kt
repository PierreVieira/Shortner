package com.pierre.shortner.feature.links.top_bar.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pierre.shortner.feature.links.top_bar.presentation.factory.ShortenerTopBarButtonsFactory
import com.pierre.shortner.feature.links.top_bar.presentation.mapper.ShortenerTopBarUiEventToUiActionMapper
import com.pierre.shortner.feature.links.top_bar.presentation.model.ShortenerTopBarButtonModel
import com.pierre.shortner.feature.links.top_bar.presentation.model.ShortenerTopBarUiAction
import com.pierre.shortner.feature.links.top_bar.presentation.model.ShortenerTopBarUiEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ShortenerTopBarViewModel(
    buttonsModelFactory: ShortenerTopBarButtonsFactory,
    private val shortenerTopBarUiEventToUiActionMapper: ShortenerTopBarUiEventToUiActionMapper,
) : ViewModel() {

    private val _uiState: MutableStateFlow<List<ShortenerTopBarButtonModel>> =
        MutableStateFlow(buttonsModelFactory.create())
    val uiState: StateFlow<List<ShortenerTopBarButtonModel>> = _uiState.asStateFlow()

    private val _uiAction: MutableSharedFlow<ShortenerTopBarUiAction> = MutableSharedFlow()
    val uiAction: SharedFlow<ShortenerTopBarUiAction> = _uiAction

    fun onEvent(event: ShortenerTopBarUiEvent) {
        viewModelScope.launch {
            _uiAction.emit(shortenerTopBarUiEventToUiActionMapper.map(event))
        }
    }
}
