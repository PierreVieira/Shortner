package com.pierre.shortner.feature.links.delete_all.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pierre.shortner.feature.links.delete_all.domain.repository.DeleteAllLinksRepository
import com.pierre.shortner.feature.links.delete_all.presentation.model.DeleteAllLinksUiEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DeleteAllLinksViewModel(
    private val repository: DeleteAllLinksRepository,
) : ViewModel() {

    private val _navigateBackUiAction: MutableSharedFlow<Unit> = MutableSharedFlow()
    val navigateBackUiAction: SharedFlow<Unit> = _navigateBackUiAction

    private val _isConfirmButtonLoading = MutableStateFlow(false)
    val isConfirmButtonLoading: StateFlow<Boolean> = _isConfirmButtonLoading

    fun onEvent(event: DeleteAllLinksUiEvent) {
        viewModelScope.launch {
            when (event) {
                DeleteAllLinksUiEvent.ON_CANCEL_CLICK -> navigateBack()
                DeleteAllLinksUiEvent.ON_CONFIRM_CLICK -> onConfirmClick()
            }
        }
    }

    private suspend fun onConfirmClick() {
        _isConfirmButtonLoading.update { true }
        repository.clear()
        navigateBack()
    }

    private suspend fun navigateBack() {
        _navigateBackUiAction.emit(Unit)
    }
}
