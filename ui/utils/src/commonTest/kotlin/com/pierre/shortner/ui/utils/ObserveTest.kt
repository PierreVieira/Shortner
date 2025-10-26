package com.pierre.shortner.ui.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class ObserveExtensionTest {

    private val mainDispatcher = StandardTestDispatcher()

    private class TestViewModel : ViewModel() {
        fun cancelScope() {
            viewModelScope.cancel()
        }
    }

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(mainDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Given a started observation When the flow emits Then values are collected in order`() = runTest(mainDispatcher) {
        // Given
        val viewModel = TestViewModel()
        val events = MutableSharedFlow<Int>(replay = 0, extraBufferCapacity = 16)
        val collected = mutableListOf<Int>()

        viewModel.observe(events) { value ->
            collected.add(value)
        }
        advanceUntilIdle()

        // When
        events.emit(1)
        events.emit(2)
        events.emit(3)
        advanceUntilIdle()

        // Then
        assertEquals(listOf(1, 2, 3), collected)
    }

    @Test
    fun `Given an active observation When the ViewModel scope is cancelled Then further emissions are ignored`() = runTest(mainDispatcher) {
        // Given
        val viewModel = TestViewModel()
        val events = MutableSharedFlow<Int>(replay = 0, extraBufferCapacity = 16)
        val collected = mutableListOf<Int>()

        viewModel.observe(events) { value ->
            collected.add(value)
        }
        advanceUntilIdle()

        // When
        events.emit(10)
        advanceUntilIdle()
        viewModel.cancelScope()
        events.emit(20)
        events.emit(30)
        advanceUntilIdle()

        // Then
        assertEquals(listOf(10), collected)
    }
}
