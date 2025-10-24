package com.pierre.shortner.feature.links.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pierre.shortner.feature.links.domain.model.UrlValidationException
import com.pierre.shortner.feature.links.domain.usecase.DeleteLinkUseCase
import com.pierre.shortner.feature.links.domain.usecase.GetAllLinksUseCase
import com.pierre.shortner.feature.links.domain.usecase.ShortenUrlUseCase
import com.pierre.shortner.feature.links.domain.usecase.ValidateUrlUseCase
import com.pierre.shortner.feature.links.presentation.model.action.LinksUiAction
import com.pierre.shortner.feature.links.presentation.model.event.LinksUiEvent
import com.pierre.shortner.feature.links.presentation.model.state.LinksUiState
import com.pierre.shortner.model.routes.DeleteAllRoute
import com.pierre.shortner.model.routes.theme.ThemeSettingsRoute
import com.pierre.shortner.ui.utils.observe
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import shortener.feature.links.presentation.generated.resources.Res
import shortener.feature.links.presentation.generated.resources.empty_url_error
import shortener.feature.links.presentation.generated.resources.invalid_url_error
import shortener.feature.links.presentation.generated.resources.shorten_url_error

class LinksViewModel(
    private val postUrlToShort: ShortenUrlUseCase,
    private val deleteLink: DeleteLinkUseCase,
    private val validateUrl: ValidateUrlUseCase,
    getAllLinks: GetAllLinksUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        LinksUiState(
            links = emptyList(),
            isLoading = false,
            urlText = "",
            expandedMenuLinkId = null,
        )
    )
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
                    _uiActions.emit(LinksUiAction.Navigate(ThemeSettingsRoute))
                }
            }

            LinksUiEvent.OnDeleteAllClick -> viewModelScope.launch {
                _uiActions.emit(LinksUiAction.Navigate(DeleteAllRoute))
            }

            is LinksUiEvent.OnDeleteLink -> viewModelScope.launch {
                deleteLink(event.id)
                _uiState.update { it.copy(expandedMenuLinkId = null) }
            }

            is LinksUiEvent.OnCopyLink -> {
                // TODO: Implement copy to clipboard functionality
                // For now, this is a placeholder that does nothing
                _uiState.update { it.copy(expandedMenuLinkId = null) }
            }

            is LinksUiEvent.OnMenuClick -> {
                _uiState.update { it.copy(expandedMenuLinkId = event.linkId) }
            }

            LinksUiEvent.OnMenuDismiss -> {
                _uiState.update { it.copy(expandedMenuLinkId = null) }
            }

            is LinksUiEvent.OnShortenUrlClick -> shortenUrl()

            is LinksUiEvent.OnUrlTextChange -> updateUrlText(event.text)
        }
    }

    private fun updateUrlText(text: String) {
        _uiState.update { it.copy(urlText = text) }
    }

    private fun shortenUrl() {
        val url = uiState.value.urlText.trim()
        validateUrl(url)
            .onSuccess {
                _uiState.update { it.copy(isLoading = true) }
                viewModelScope.launch {
                    postUrlToShort(url)
                        .onSuccess {
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                urlText = "",
                            )
                        }
                        .onFailure {
                            _uiState.update { it.copy(isLoading = false) }
                            _uiActions.emit(LinksUiAction.ShowSnackbar(Res.string.shorten_url_error))
                        }
                }
            }.onFailure { failure ->
                viewModelScope.launch {
                    if (failure is UrlValidationException) {
                        val message = when (failure) {
                            is UrlValidationException.Empty -> Res.string.empty_url_error
                            is UrlValidationException.Invalid -> Res.string.invalid_url_error
                        }
                        _uiActions.emit(LinksUiAction.ShowSnackbar(message))
                    }
                }
            }
    }
}
