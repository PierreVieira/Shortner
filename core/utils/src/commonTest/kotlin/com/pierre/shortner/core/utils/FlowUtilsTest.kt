package com.pierre.shortner.core.utils

import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.test.Test
import kotlin.test.assertEquals

class FlowUtilsTest {

    @Test
    fun `given MutableStateFlow when updateValue then state holds new value`() {
        // Given
        val state = MutableStateFlow(0)
        // When
        state.updateValue(5)
        // Then
        assertEquals(5, state.value)
    }

    @Test
    fun `given MutableStateFlow with custom type when updateValue then replaces instance`() {
        // Given
        data class User(val name: String, val age: Int)
        val state = MutableStateFlow(User("Alice", 21))
        val newUser = User("Bob", 30)
        // When
        state.updateValue(newUser)
        // Then
        assertEquals(newUser, state.value)
    }
}
