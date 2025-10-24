package com.pierre.shortner.feature.links.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pierre.shortner.core.utils.toFormattedString
import com.pierre.shortner.feature.links.domain.model.Link
import com.pierre.shortner.feature.links.domain.usecase.DeleteLinkUseCase
import com.pierre.shortner.feature.links.domain.usecase.GetAllLinksUseCase
import com.pierre.shortner.feature.links.presentation.model.LinkPresentationModel
import com.pierre.shortner.feature.links.presentation.model.action.LinksUiAction
import com.pierre.shortner.feature.links.presentation.model.event.LinksUiEvent
import com.pierre.shortner.feature.links.presentation.model.state.LinksUiState
import com.pierre.shortner.ui.utils.observe
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LinksViewModel(
    private val deleteLink: DeleteLinkUseCase,
    getAllLinks: GetAllLinksUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        LinksUiState(
            links = emptyList(),
            isSendButtonLoading = false,
            urlText = "",
        )
    )
    val uiState = _uiState.asStateFlow()

    private val _uiActions = MutableSharedFlow<LinksUiAction>()
    val uiActions = _uiActions.asSharedFlow()

    init {
        observe(getAllLinks()) { links ->
            val linkPresentationModels = links.map { link ->
                link.toPresentationModel()
            }
            _uiState.update { it.copy(links = linkPresentationModels) }
        }
    }

    private fun Link.toPresentationModel(): LinkPresentationModel = LinkPresentationModel(
        id = id,
        originalUrl = originalUrl,
        shortenedUrl = shortenedUrl,
        alias = alias,
        createdAt = createdAt.toFormattedString(),
        isCardExpanded = false,
        isMenuExpanded = false
    )


    fun onEvent(event: LinksUiEvent) {
        when (event) {
            is LinksUiEvent.OnDeleteLink -> viewModelScope.launch {
                deleteLink(event.id)
                updateLinkById(event.id) { it.copy(isMenuExpanded = false) }
            }

            is LinksUiEvent.OnCopyLink -> {
                // TODO: Implement copy to clipboard functionality
                // For now, this is a placeholder that does nothing
                updateLinkById(event.id) { it.copy(isMenuExpanded = false) }
            }

            is LinksUiEvent.OnMenuClick -> {
                updateAllLinks { linkModel ->
                    if (linkModel.id == event.linkId) {
                        linkModel.copy(isMenuExpanded = true)
                    } else {
                        linkModel.copy(isMenuExpanded = false)
                    }
                }
            }

            LinksUiEvent.OnMenuDismiss -> {
                updateAllLinks { it.copy(isMenuExpanded = false) }
            }

            is LinksUiEvent.OnToggleCardCollapse -> {
                updateLinkById(event.linkId) { it.copy(isCardExpanded = !it.isCardExpanded) }
            }
        }
    }

    private fun updateLinkById(linkId: Long, transform: (LinkPresentationModel) -> LinkPresentationModel) {
        _uiState.update { currentState ->
            currentState.copy(
                links = currentState.links.map { linkModel ->
                    if (linkModel.id == linkId) {
                        transform(linkModel)
                    } else {
                        linkModel
                    }
                }
            )
        }
    }
    
    private fun updateAllLinks(transform: (LinkPresentationModel) -> LinkPresentationModel) {
        _uiState.update { currentState ->
            currentState.copy(
                links = currentState.links.map(transform)
            )
        }
    }
}
