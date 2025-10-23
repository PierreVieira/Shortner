package com.pierre.shortner.core.utils

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class StringUtilsTest {

    // toUpperCamelCase
    @Test
    fun `given lowercase words with spaces when toUpperCamelCase then capitalizes each`() {
        // Given
        val input = "spider man"
        // When
        val result = input.toUpperCamelCase()
        // Then
        assertEquals("Spider Man", result)
    }

    @Test
    fun `given mixed case and extra spaces when toUpperCamelCase then trims and formats`() {
        // Given
        val input = "  GAME   of   thrones  "
        // When
        val result = input.toUpperCamelCase()
        // Then
        assertEquals("Game Of Thrones", result)
    }

    @Test
    fun `given empty string when toUpperCamelCase then returns empty`() {
        // Given
        val input = ""
        // When
        val result = input.toUpperCamelCase()
        // Then
        assertEquals("", result)
    }

    // safeYearOrNull
    @Test
    fun `given iso date when safeYearOrNull then returns year`() {
        // Given
        val date = "2016-07-06"
        // When
        val year = date.safeYearOrNull()
        // Then
        assertEquals(2016, year)
    }

    @Test
    fun `given null when safeYearOrNull then returns null`() {
        // Given
        val date: String? = null
        // When
        val year = date.safeYearOrNull()
        // Then
        assertNull(year)
    }

    @Test
    fun `given text with embedded year when safeYearOrNull then extracts first 4 digit year`() {
        // Given
        val text = "The show started in 1999 and ended in 2005"
        // When
        val year = text.safeYearOrNull()
        // Then
        assertEquals(1999, year)
    }

    @Test
    fun `given invalid year when safeYearOrNull then returns null`() {
        // Given
        val text = "Year: 1899"
        // When
        val year = text.safeYearOrNull()
        // Then
        assertNull(year)
    }
}
