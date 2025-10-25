package com.pierre.shortner.feature.links.input.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.pierre.shortner.feature.links.input.domain.model.ShortenUrlStep
import com.pierre.shortner.feature.links.input.domain.usecase.ShortenUrl
import com.pierre.shortner.feature.links.input.presentation.factory.ValidationErrorMessageFactory
import com.pierre.shortner.feature.links.input.presentation.model.LinkInputUiEvent
import com.pierre.shortner.feature.links.input.presentation.model.LinkInputUiState
import com.pierre.shortner.feature.links.input.presentation.model.LinksUiAction
import com.pierre.shortner.ui.utils.observe
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LinkInputViewModel(
    private val shortenUrl: ShortenUrl,
    private val validationErrorMessageFactory: ValidationErrorMessageFactory,
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
            LinkInputUiEvent.OnShortenUrlClick -> onShortenUrlClick()
            is LinkInputUiEvent.OnUrlTextChange -> updateUrlText(event.text)
        }
    }

    private fun onShortenUrlClick() {
        observe(shortenUrl(uiState.value.urlText)) { step ->
            when (step) {
                ShortenUrlStep.InProgress -> setLoadingState(true)
                ShortenUrlStep.Success -> {
                    setLoadingState(false)
                    updateUrlText("")
                }
                is ShortenUrlStep.Error -> {
                    setLoadingState(false)
                    _uiActions.emit(
                        LinksUiAction.ShowSnackbar(
                            resourceId = validationErrorMessageFactory.create(step.throwable)
                        )
                    )
                }
            }
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        _uiState.update { it.copy(isSendButtonLoading = isLoading) }
    }

    private fun updateUrlText(text: String) {
        _uiState.update { it.copy(urlText = text) }
    }
}
