package com.pierre.shortner.feature.links.delete_link.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.pierre.shortner.feature.links.delete_link.domain.repository.DeleteLinkRepository
import com.pierre.shortner.feature.links.delete_link.presentation.model.DeleteLinkEvent
import com.pierre.shortner.model.routes.links.delete.DeleteLinkRoute
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DeleteLinkViewModel(
    private val repository: DeleteLinkRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _navigateBack: MutableSharedFlow<Unit> = MutableSharedFlow()
    val navigateBack: SharedFlow<Unit> = _navigateBack

    private val _uiState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val uiState: StateFlow<Boolean> = _uiState

    private val id = savedStateHandle.toRoute<DeleteLinkRoute>().id

    fun onEvent(event: DeleteLinkEvent) {
        viewModelScope.launch {
            when (event) {
                DeleteLinkEvent.ON_CANCEL_CLICK -> navigateBack()
                DeleteLinkEvent.ON_CONFIRM_CLICK -> {
                    _uiState.update { true }
                    repository.deleteLink(id)
                    navigateBack()
                }
            }
        }
    }

    private suspend fun navigateBack() {
        _navigateBack.emit(Unit)
    }
}
