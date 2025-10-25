package com.pierre.shortner.feature.links.delete_link.presentation

import androidx.lifecycle.SavedStateHandle
import com.pierre.shortner.feature.links.delete_link.domain.repository.DeleteLinkRepository
import com.pierre.shortner.feature.links.delete_link.presentation.model.DeleteLinkEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class DeleteLinkViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private val fakeRepository = FakeDeleteLinkRepository()
    private val savedStateHandle = SavedStateHandle(mapOf("id" to ID))
    private lateinit var viewModel: DeleteLinkViewModel

    @BeforeTest
    fun setUp() {
        viewModel = DeleteLinkViewModel(fakeRepository, savedStateHandle)
        Dispatchers.setMain(dispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given viewmodel when initialized then has correct initial state`() = runTest(dispatcher) {
        // When
        val uiState = viewModel.uiState.first()

        // Then
        assertFalse(uiState)
    }

    @Test
    fun `given viewmodel when ON_CANCEL_CLICK then emits navigate back action`() =
        runTest(dispatcher) {
            // Given
            val actions = mutableListOf<Unit>()
            val job: Job = launch { viewModel.navigateBack.collect { actions.add(it) } }

            // When
            viewModel.onEvent(DeleteLinkEvent.ON_CANCEL_CLICK)
            advanceUntilIdle()
            job.cancel()

            // Then
            assertEquals(1, actions.size)
        }

    @Test
    fun `given viewmodel when ON_CONFIRM_CLICK then calls repository deleteLink and emits navigate back`() =
        runTest(dispatcher) {
            // Given
            val actions = mutableListOf<Unit>()
            val job: Job = launch { viewModel.navigateBack.collect { actions.add(it) } }

            // When
            viewModel.onEvent(DeleteLinkEvent.ON_CONFIRM_CLICK)
            advanceUntilIdle()
            job.cancel()

            // Then
            assertTrue(fakeRepository.deleteLinkCalled)
            assertEquals(ID, fakeRepository.deletedId)
            assertEquals(1, actions.size)
        }

    @Test
    fun `given viewmodel when ON_CONFIRM_CLICK then sets loading state correctly`() =
        runTest(dispatcher) {
            // Given
            val loadingStates = mutableListOf<Boolean>()
            val job: Job = launch { viewModel.uiState.collect { loadingStates.add(it) } }

            // When
            viewModel.onEvent(DeleteLinkEvent.ON_CONFIRM_CLICK)
            advanceUntilIdle()
            job.cancel()

            // Then
            assertEquals(listOf(false, true), loadingStates)
        }

    @Test
    fun `given viewmodel when multiple events then handles them correctly`() = runTest(dispatcher) {
        // Given
        val actions = mutableListOf<Unit>()
        val job: Job = launch { viewModel.navigateBack.collect { actions.add(it) } }

        // When
        viewModel.onEvent(DeleteLinkEvent.ON_CANCEL_CLICK)
        viewModel.onEvent(DeleteLinkEvent.ON_CONFIRM_CLICK)
        advanceUntilIdle()
        job.cancel()

        // Then
        assertTrue(fakeRepository.deleteLinkCalled)
        assertEquals(ID, fakeRepository.deletedId)
        assertEquals(2, actions.size)
    }

    private class FakeDeleteLinkRepository : DeleteLinkRepository {
        var deleteLinkCalled = false
        var deletedId: Long? = null

        override suspend fun deleteLink(id: Long) {
            deleteLinkCalled = true
            deletedId = id
        }
    }

    companion object {
        private const val ID = 123L
    }
}
