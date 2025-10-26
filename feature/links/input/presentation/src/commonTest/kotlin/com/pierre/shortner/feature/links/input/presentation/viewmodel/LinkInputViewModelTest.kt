package com.pierre.shortner.feature.links.input.presentation.viewmodel

import com.pierre.shortner.feature.links.input.domain.model.ShortenUrlStep
import com.pierre.shortner.feature.links.input.domain.usecase.ShortenUrl
import com.pierre.shortner.feature.links.input.presentation.factory.ValidationErrorMessageFactory
import com.pierre.shortner.feature.links.input.presentation.model.LinkInputUiEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.jetbrains.compose.resources.StringResource
import shortener.feature.links.input.presentation.generated.resources.Res
import shortener.feature.links.input.presentation.generated.resources.shorten_url_error
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class LinkInputViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val shortenUrl = MockShortenUrl()
    private val validationErrorMessageFactory = MockValidationErrorMessageFactory()
    private val viewModel = LinkInputViewModel(shortenUrl, validationErrorMessageFactory)

    init {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `given OnUrlTextChange event when onEvent then updates urlText in state`() = runTest {
        // Given
        val newText = "https://example.com"
        val event = LinkInputUiEvent.OnUrlTextChange(newText)

        // When
        viewModel.onEvent(event)

        // Then
        assertEquals(newText, viewModel.uiState.value.urlText)
    }

    @Test
    fun `given OnShortenUrlClick with success when onEvent then clears urlText and stops loading`() =
        runTest(testDispatcher) {
            // Given
            val testUrl = "https://example.com"
            viewModel.onEvent(LinkInputUiEvent.OnUrlTextChange(testUrl))
            shortenUrl.shortenUrlFlow = flowOf(
                ShortenUrlStep.InProgress,
                ShortenUrlStep.Success
            )

            // When
            viewModel.onEvent(LinkInputUiEvent.OnShortenUrlClick)
            advanceUntilIdle()

            // Then
            assertFalse(viewModel.uiState.value.isSendButtonLoading)
            assertEquals("", viewModel.uiState.value.urlText)
        }

    @Test
    fun `given OnShortenUrlClick with error when onEvent then stops loading`() =
        runTest(testDispatcher) {
            // Given
            val testUrl = "https://example.com"
            val testException = RuntimeException("Test error")
            viewModel.onEvent(LinkInputUiEvent.OnUrlTextChange(testUrl))
            shortenUrl.shortenUrlFlow = flowOf(
                ShortenUrlStep.InProgress,
                ShortenUrlStep.Error(testException)
            )

            // When
            viewModel.onEvent(LinkInputUiEvent.OnShortenUrlClick)
            advanceUntilIdle()

            // Then
            assertFalse(viewModel.uiState.value.isSendButtonLoading)
        }

    @Test
    fun `given OnShortenUrlClick when onEvent then sets loading state during InProgress`() =
        runTest(testDispatcher) {
            // Given
            val testUrl = "https://example.com"
            viewModel.onEvent(LinkInputUiEvent.OnUrlTextChange(testUrl))
            shortenUrl.shortenUrlFlow = flowOf(ShortenUrlStep.InProgress)

            // When
            viewModel.onEvent(LinkInputUiEvent.OnShortenUrlClick)
            advanceUntilIdle()

            // Then
            assertTrue(viewModel.uiState.value.isSendButtonLoading)
        }

    private class MockShortenUrl : ShortenUrl {
        var shortenUrlFlow: Flow<ShortenUrlStep> = flowOf(ShortenUrlStep.Success)

        override fun invoke(url: String) = shortenUrlFlow
    }

    private class MockValidationErrorMessageFactory : ValidationErrorMessageFactory {
        override fun create(failure: Throwable): StringResource = Res.string.shorten_url_error
    }
}
