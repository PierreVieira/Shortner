package com.pierre.shortner.feature.links.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pierre.shortner.feature.links.domain.usecase.DeleteAllLinksUseCase
import com.pierre.shortner.feature.links.domain.usecase.DeleteLinkUseCase
import com.pierre.shortner.feature.links.domain.usecase.GetAllLinksUseCase
import com.pierre.shortner.feature.links.domain.usecase.ShortenUrlUseCase
import com.pierre.shortner.feature.links.domain.usecase.ValidateUrlUseCase
import com.pierre.shortner.feature.links.domain.usecase.ValidationResult
import com.pierre.shortner.feature.links.presentation.model.LinksUiAction
import com.pierre.shortner.feature.links.presentation.model.LinksUiEvent
import com.pierre.shortner.feature.links.presentation.model.LinksUiState
import com.pierre.shortner.ui.utils.observe
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LinksViewModel(
    private val shortenUrlUseCase: ShortenUrlUseCase,
    private val deleteLinkUseCase: DeleteLinkUseCase,
    private val deleteAllLinksUseCase: DeleteAllLinksUseCase,
    private val validateUrlUseCase: ValidateUrlUseCase,
    getAllLinks: GetAllLinksUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LinksUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiActions = MutableSharedFlow<LinksUiAction>()
    val uiActions = _uiActions.asSharedFlow()

    init {
        observe(getAllLinks()) { links ->
            _uiState.update { it.copy(links = links) }
        }
    }

    fun onEvent(event: LinksUiEvent) {
        when (event) {
            LinksUiEvent.OnThemeClick -> {
                viewModelScope.launch {
                    _uiActions.emit(LinksUiAction.NavigateToTheme)
                }
            }

            LinksUiEvent.OnDeleteAllClick -> {
                _uiState.value = _uiState.value.copy(showDeleteConfirmation = true)
            }

            LinksUiEvent.OnDeleteAllConfirm -> {
                viewModelScope.launch {
                    try {
                        deleteAllLinksUseCase()
                        _uiState.value = _uiState.value.copy(showDeleteConfirmation = false)
                        _uiActions.emit(LinksUiAction.ShowSnackbar("All links deleted"))
                    } catch (e: Exception) {
                        _uiActions.emit(LinksUiAction.ShowSnackbar("Failed to delete all links"))
                    }
                }
            }

            LinksUiEvent.OnDeleteAllCancel -> {
                _uiState.value = _uiState.value.copy(showDeleteConfirmation = false)
            }

            is LinksUiEvent.OnShortenUrl -> {
                shortenUrl(event.url)
            }

            is LinksUiEvent.OnDeleteLink -> {
                viewModelScope.launch {
                    try {
                        deleteLinkUseCase(event.id)
                        _uiActions.emit(LinksUiAction.ShowSnackbar("Link deleted"))
                    } catch (_: Exception) {
                        _uiActions.emit(LinksUiAction.ShowSnackbar("Failed to delete link"))
                    }
                }
            }

            is LinksUiEvent.OnUrlTextChange -> updateUrlText(event.text)
        }
    }

    private fun updateUrlText(text: String) {
        _uiState.update { it.copy(urlText = text) }
    }

    private fun shortenUrl(url: String) {
        when (validateUrlUseCase(url)) {
            is ValidationResult.Empty -> {
                viewModelScope.launch {
                    _uiActions.emit(LinksUiAction.ShowSnackbar("Please enter a URL"))
                }
                return
            }
            is ValidationResult.Invalid -> {
                viewModelScope.launch {
                    _uiActions.emit(LinksUiAction.ShowSnackbar("Please enter a valid URL"))
                }
                return
            }
            is ValidationResult.Valid -> {
                // Continue with shortening
            }
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                shortenUrlUseCase(url).fold(
                    onSuccess = { link ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            urlText = ""
                        )
                    },
                    onFailure = { error ->
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        _uiActions.emit(LinksUiAction.ShowSnackbar("Failed to shorten URL. Please try again."))
                    }
                )
            } catch (_: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false)
                _uiActions.emit(LinksUiAction.ShowSnackbar("Failed to shorten URL. Please try again."))
            }
        }
    }

}
