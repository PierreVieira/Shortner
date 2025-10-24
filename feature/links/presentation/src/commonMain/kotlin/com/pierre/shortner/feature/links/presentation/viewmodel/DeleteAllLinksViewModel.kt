package com.pierre.shortner.feature.links.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pierre.shortner.feature.links.domain.usecase.DeleteAllLinksUseCase
import com.pierre.shortner.feature.links.presentation.model.event.DeleteAllLinksUiEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class DeleteAllLinksViewModel(
    private val deleteAllLinks: DeleteAllLinksUseCase,
) : ViewModel() {

    private val _navigateBackUiAction: MutableSharedFlow<Unit> = MutableSharedFlow()
    val navigateBackUiAction: SharedFlow<Unit> = _navigateBackUiAction

    fun onEvent(event: DeleteAllLinksUiEvent) {
        viewModelScope.launch {
            when (event) {
                DeleteAllLinksUiEvent.OnCancelClick -> _navigateBackUiAction.emit(Unit)
                DeleteAllLinksUiEvent.OnConfirmClick -> deleteAllLinks()
            }
        }
    }
}
