package com.pierre.shortner.feature.links.input.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pierre.shortner.feature.links.input.domain.model.UrlValidationException
import com.pierre.shortner.feature.links.input.domain.usecase.ShortenUrlUseCase
import com.pierre.shortner.feature.links.input.domain.usecase.ValidateUrlUseCase
import com.pierre.shortner.feature.links.input.presentation.model.LinkInputUiEvent
import com.pierre.shortner.feature.links.input.presentation.model.LinkInputUiState
import com.pierre.shortner.feature.links.input.presentation.model.LinksUiAction
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import shortener.feature.links.input.presentation.generated.resources.Res
import shortener.feature.links.input.presentation.generated.resources.empty_url_error
import shortener.feature.links.input.presentation.generated.resources.invalid_url_error
import shortener.feature.links.input.presentation.generated.resources.link_already_added
import shortener.feature.links.input.presentation.generated.resources.shorten_url_error

class LinkInputViewModel(
    private val validateUrl: ValidateUrlUseCase,
    private val postUrlToShort: ShortenUrlUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        LinkInputUiState(
            urlText = "",
            isSendButtonLoading = false,
        )
    )
    val uiState = _uiState.asStateFlow()

    private val _uiActions = MutableSharedFlow<LinksUiAction>()
    val uiActions = _uiActions.asSharedFlow()

    fun onEvent(event: LinkInputUiEvent) {
        when (event) {
            LinkInputUiEvent.OnShortenUrlClick -> shortenUrl()
            is LinkInputUiEvent.OnUrlTextChange -> updateUrlText(event.text)
        }
    }

    private fun shortenUrl() {
        val url = uiState.value.urlText.trim()
        viewModelScope.launch {
            validateUrl(url)
                .onSuccess {
                    setLoadingState(true)

                    postUrlToShort(url)
                        .onSuccess {
                            setLoadingState(false)
                            updateUrlText("")
                        }
                        .onFailure {
                            setLoadingState(false)
                            _uiActions.emit(LinksUiAction.ShowSnackbar(Res.string.shorten_url_error))
                        }
                }
                .onFailure { failure ->
                    viewModelScope.launch {
                        _uiActions.emit(LinksUiAction.ShowSnackbar(getValidationErrorMessage(failure)))
                    }
                }
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        _uiState.update { it.copy(isSendButtonLoading = isLoading) }
    }

    private fun getValidationErrorMessage(failure: Throwable): StringResource =
        if (failure is UrlValidationException) {
            when (failure) {
                is UrlValidationException.Empty -> Res.string.empty_url_error
                is UrlValidationException.Invalid -> Res.string.invalid_url_error
                is UrlValidationException.LinkAlreadyAdded -> Res.string.link_already_added
            }
        } else {
            Res.string.shorten_url_error
        }

    private fun updateUrlText(text: String) {
        _uiState.update { it.copy(urlText = text) }
    }
}
