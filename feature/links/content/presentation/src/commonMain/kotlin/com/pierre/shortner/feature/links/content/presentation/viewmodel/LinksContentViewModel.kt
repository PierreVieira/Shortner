package com.pierre.shortner.feature.links.content.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pierre.shortner.feature.links.content.domain.usecase.GetAllLinks
import com.pierre.shortner.feature.links.content.presentation.mapper.LinkModelMapper
import com.pierre.shortner.feature.links.content.presentation.model.LinkPresentationModel
import com.pierre.shortner.feature.links.content.presentation.model.action.LinksUiAction
import com.pierre.shortner.feature.links.content.presentation.model.event.LinksUiEvent
import com.pierre.shortner.feature.links.content.presentation.model.state.LinkListUiState
import com.pierre.shortner.feature.links.content.presentation.model.state.LinksContentUiState
import com.pierre.shortner.model.routes.links.delete.DeleteLinkRoute
import com.pierre.shortner.ui.utils.observe
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LinksContentViewModel(
    getAllLinks: GetAllLinks,
    private val linkModelMapper: LinkModelMapper,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        LinksContentUiState(
            links = LinkListUiState.Loading,
            isSendButtonLoading = false,
            urlText = "",
        )
    )
    val uiState = _uiState.asStateFlow()

    private val _uiActions = MutableSharedFlow<LinksUiAction>()
    val uiActions = _uiActions.asSharedFlow()

    private val loadedLinks get() = (uiState.value.links as? LinkListUiState.Loaded)?.data

    init {
        observe(getAllLinks()) { links ->
            val linkPresentationModels = links.map {
                linkModelMapper.toPresentation(it)
            }
            _uiState.update {
                it.copy(links = LinkListUiState.Loaded(linkPresentationModels))
            }
        }
    }

    fun onEvent(event: LinksUiEvent) {
        when (event) {
            is LinksUiEvent.OnDeleteLink -> viewModelScope.launch {
                _uiActions.emit(LinksUiAction.Navigate(DeleteLinkRoute(event.id)))
            }

            is LinksUiEvent.OnOriginalLinkClick ->
                copyLinkToClipboard(event.id) { it.originalUrl }

            is LinksUiEvent.OnOriginalLinkLongPress ->
                copyLinkToClipboard(event.id) { it.originalUrl }

            is LinksUiEvent.OnShortenedLinkClick ->
                copyLinkToClipboard(event.id) { it.shortenedUrl }

            is LinksUiEvent.OnShortenedLinkLongPress ->
                copyLinkToClipboard(event.id) { it.shortenedUrl }

            is LinksUiEvent.OnToggleCardCollapse -> {
                updateLinkById(event.linkId) { it.copy(isCardExpanded = !it.isCardExpanded) }
            }
        }
    }

    private fun copyLinkToClipboard(linkId: Long, urlExtractor: (LinkPresentationModel) -> String) {
        val safeLinks = loadedLinks ?: return
        val link = safeLinks.find { it.id == linkId }
        link?.let { linkModel ->
            viewModelScope.launch {
                _uiActions.emit(LinksUiAction.CopyToClipboard(urlExtractor(linkModel)))
            }
        }
    }

    private fun updateLinkById(
        linkId: Long,
        transform: (LinkPresentationModel) -> LinkPresentationModel,
    ) {
        val safeLinks = loadedLinks ?: return
        _uiState.update { currentState ->
            currentState.copy(
                links = LinkListUiState.Loaded(
                    safeLinks.map { linkModel ->
                        if (linkModel.id == linkId) {
                            transform(linkModel)
                        } else {
                            linkModel
                        }
                    }
                )
            )
        }
    }
}
