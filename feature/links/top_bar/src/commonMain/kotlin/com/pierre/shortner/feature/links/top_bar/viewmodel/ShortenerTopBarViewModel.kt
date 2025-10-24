package com.pierre.shortner.feature.links.top_bar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pierre.shortner.feature.links.top_bar.factory.ShortenerTopBarButtonsFactory
import com.pierre.shortner.feature.links.top_bar.mapper.ShortenerTopBarUiEventMapper
import com.pierre.shortner.feature.links.top_bar.model.ShortenerTopBarButtonModel
import com.pierre.shortner.feature.links.top_bar.model.ShortenerTopBarUiAction
import com.pierre.shortner.feature.links.top_bar.model.ShortenerTopBarUiEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ShortenerTopBarViewModel(
    buttonsModelFactory: ShortenerTopBarButtonsFactory,
    private val uiEventToNavigationRouteMapper: ShortenerTopBarUiEventMapper
) : ViewModel() {

    private val _uiState: MutableStateFlow<List<ShortenerTopBarButtonModel>> =
        MutableStateFlow(buttonsModelFactory.create())
    val uiState: StateFlow<List<ShortenerTopBarButtonModel>> = _uiState.asStateFlow()

    private val _uiAction: MutableSharedFlow<ShortenerTopBarUiAction> = MutableSharedFlow()
    val uiAction: SharedFlow<ShortenerTopBarUiAction> = _uiAction

    fun onEvent(event: ShortenerTopBarUiEvent) {
        val route = uiEventToNavigationRouteMapper.map(event)
        viewModelScope.launch {
            _uiAction.emit(ShortenerTopBarUiAction.NavigateToRoute(route))
        }
    }
}
