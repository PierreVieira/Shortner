package com.pierre.shortner.feature.links.content.presentation.viewmodel

import com.pierre.shortner.feature.links.content.domain.model.LinkDomainModel
import com.pierre.shortner.feature.links.content.domain.usecase.GetAllLinks
import com.pierre.shortner.feature.links.content.presentation.mapper.LinkModelMapper
import com.pierre.shortner.feature.links.content.presentation.model.LinkPresentationModel
import com.pierre.shortner.feature.links.content.presentation.model.state.LinkListUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class LinksContentViewModelTest {

    @Test
    fun `given viewmodel when initialized then starts with loading state`() = runTest {
        // Given
        val fakeGetAllLinks = FakeGetAllLinks(flowOf(emptyList()))
        val fakeMapper = FakeLinkModelMapper()

        // When
        val viewModel = LinksContentViewModel(fakeGetAllLinks, fakeMapper)

        // Then
        val initialState = viewModel.uiState.value
        assertTrue(initialState.links is LinkListUiState.Loading)
        assertEquals(false, initialState.isSendButtonLoading)
        assertEquals("", initialState.urlText)
    }

    @Test
    fun `given viewmodel when initialized then uiActions is accessible`() = runTest {
        // Given
        val fakeGetAllLinks = FakeGetAllLinks(flowOf(emptyList()))
        val fakeMapper = FakeLinkModelMapper()

        // When
        val viewModel = LinksContentViewModel(fakeGetAllLinks, fakeMapper)

        // Then
        val actions = viewModel.uiActions
        assertTrue(actions.replayCache.isEmpty())
    }

    @Test
    fun `given viewmodel when initialized then uiState is accessible`() = runTest {
        // Given
        val fakeGetAllLinks = FakeGetAllLinks(flowOf(emptyList()))
        val fakeMapper = FakeLinkModelMapper()

        // When
        val viewModel = LinksContentViewModel(fakeGetAllLinks, fakeMapper)

        // Then
        val state = viewModel.uiState.value
        assertTrue(state.links is LinkListUiState.Loading)
    }

    private class FakeGetAllLinks(
        private val linksFlow: Flow<List<LinkDomainModel>>
    ) : GetAllLinks {
        override fun invoke(): Flow<List<LinkDomainModel>> = linksFlow
    }

    private class FakeLinkModelMapper(
        private val presentationModel: LinkPresentationModel = LinkPresentationModel(
            id = 1L,
            originalUrl = "https://www.example.com",
            shortenedUrl = "https://short.ly/abc123",
            alias = "example-link",
            createdAt = "15/01/2024 at 10:30:45",
            isCardExpanded = false,
            isMenuExpanded = false,
        )
    ) : LinkModelMapper {
        override suspend fun toPresentation(domainModel: LinkDomainModel): LinkPresentationModel =
            presentationModel.copy(id = domainModel.id)
    }
}
