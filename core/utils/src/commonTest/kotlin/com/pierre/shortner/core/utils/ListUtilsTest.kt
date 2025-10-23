package com.pierre.shortner.core.utils

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ListUtilsTest {

    @Test
    fun `given non empty list when index is last then isLastIndex returns true`() {
        // Given
        val list = listOf("a", "b", "c")
        // When
        val result = list.isLastIndex(2)
        // Then
        assertTrue(result)
    }

    @Test
    fun `given non empty list when index is not last then isLastIndex returns false`() {
        // Given
        val list = listOf(1, 2, 3, 4)
        // When
        val result = list.isLastIndex(1)
        // Then
        assertFalse(result)
    }

    @Test
    fun `given empty list when any non negative index then isLastIndex returns false`() {
        // Given
        val list = emptyList<Int>()
        // When
        val result0 = list.isLastIndex(0)
        val result1 = list.isLastIndex(1)
        // Then
        assertFalse(result0)
        assertFalse(result1)
    }
}
