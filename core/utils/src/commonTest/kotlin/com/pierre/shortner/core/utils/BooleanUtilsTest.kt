package com.pierre.shortner.core.utils

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BooleanUtilsTest {

    @Test
    fun `given null boolean when orFalse then returns false`() {
        // Given
        val value: Boolean? = null
        // When
        val result = value.orFalse()
        // Then
        assertFalse(result)
    }

    @Test
    fun `given true boolean when orFalse then returns true`() {
        // Given
        val value: Boolean? = true
        // When
        val result = value.orFalse()
        // Then
        assertTrue(result)
    }

    @Test
    fun `given null boolean when orTrue then returns true`() {
        // Given
        val value: Boolean? = null
        // When
        val result = value.orTrue()
        // Then
        assertTrue(result)
    }

    @Test
    fun `given false boolean when orTrue then returns false`() {
        // Given
        val value: Boolean? = false
        // When
        val result = value.orTrue()
        // Then
        assertFalse(result)
    }
}
