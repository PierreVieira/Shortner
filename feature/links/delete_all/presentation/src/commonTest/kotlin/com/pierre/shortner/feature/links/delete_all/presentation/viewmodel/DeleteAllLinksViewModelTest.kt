package com.pierre.shortner.feature.links.delete_all.presentation.viewmodel

import com.pierre.shortner.feature.links.delete_all.domain.repository.DeleteAllLinksRepository
import com.pierre.shortner.feature.links.delete_all.presentation.model.DeleteAllLinksUiEvent
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
class DeleteAllLinksViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private val fakeRepository = FakeDeleteAllLinksRepository()
    private val viewModel = DeleteAllLinksViewModel(fakeRepository)

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given viewmodel when initialized then has correct initial state`() = runTest(dispatcher) {
        // When
        val isConfirmButtonLoading = viewModel.isConfirmButtonLoading.first()

        // Then
        assertFalse(isConfirmButtonLoading)
    }

    @Test
    fun `given viewmodel when ON_CANCEL_CLICK then emits navigate back action`() = runTest(dispatcher) {
        // Given
        val actions = mutableListOf<Unit>()
        val job: Job = launch { viewModel.navigateBackUiAction.collect { actions.add(it) } }

        // When
        viewModel.onEvent(DeleteAllLinksUiEvent.ON_CANCEL_CLICK)
        advanceUntilIdle()
        job.cancel()

        // Then
        assertEquals(1, actions.size)
    }

    @Test
    fun `given viewmodel when ON_CONFIRM_CLICK then calls repository clear and emits navigate back`() = runTest(dispatcher) {
        // Given
        val actions = mutableListOf<Unit>()
        val job: Job = launch { viewModel.navigateBackUiAction.collect { actions.add(it) } }

        // When
        viewModel.onEvent(DeleteAllLinksUiEvent.ON_CONFIRM_CLICK)
        advanceUntilIdle()
        job.cancel()

        // Then
        assertTrue(fakeRepository.clearCalled)
        assertEquals(1, actions.size)
    }

    @Test
    fun `given viewmodel when ON_CONFIRM_CLICK then sets loading state correctly`() = runTest(dispatcher) {
        // Given
        val loadingStates = mutableListOf<Boolean>()
        val job: Job = launch { viewModel.isConfirmButtonLoading.collect { loadingStates.add(it) } }

        // When
        viewModel.onEvent(DeleteAllLinksUiEvent.ON_CONFIRM_CLICK)
        advanceUntilIdle()
        job.cancel()

        // Then
        assertEquals(listOf(false, true), loadingStates)
    }

    @Test
    fun `given viewmodel when multiple events then handles them correctly`() = runTest(dispatcher) {
        // Given
        val actions = mutableListOf<Unit>()
        val job: Job = launch { viewModel.navigateBackUiAction.collect { actions.add(it) } }

        // When
        viewModel.onEvent(DeleteAllLinksUiEvent.ON_CANCEL_CLICK)
        viewModel.onEvent(DeleteAllLinksUiEvent.ON_CONFIRM_CLICK)
        advanceUntilIdle()
        job.cancel()

        // Then
        assertTrue(fakeRepository.clearCalled)
        assertEquals(2, actions.size)
    }

    private class FakeDeleteAllLinksRepository : DeleteAllLinksRepository {
        var clearCalled = false

        override suspend fun clear() {
            clearCalled = true
        }
    }
}
