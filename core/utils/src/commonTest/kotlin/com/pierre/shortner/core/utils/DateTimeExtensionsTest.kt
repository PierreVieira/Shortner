package com.pierre.shortner.core.utils

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlin.test.Test
import kotlin.test.assertEquals

class DateTimeExtensionsTest {

    @Test
    fun `given LocalDateTime with single digit values when toFormattedString then formats correctly with padding`() {
        // Given
        val dateTime = LocalDateTime(
            date = LocalDate(2023, 1, 5),
            time = LocalTime(9, 7, 3)
        )
        val connectorText = "at"

        // When
        val result = dateTime.toFormattedString(connectorText)

        // Then
        assertEquals("05/01/2023 at 09:07:03", result)
    }

    @Test
    fun `given LocalDateTime with double digit values when toFormattedString then formats correctly`() {
        // Given
        val dateTime = LocalDateTime(
            date = LocalDate(2023, 12, 25),
            time = LocalTime(23, 59, 59)
        )
        val connectorText = "at"

        // When
        val result = dateTime.toFormattedString(connectorText)

        // Then
        assertEquals("25/12/2023 at 23:59:59", result)
    }

    @Test
    fun `given LocalDateTime with different connector text when toFormattedString then uses custom connector`() {
        // Given
        val dateTime = LocalDateTime(
            date = LocalDate(2024, 6, 15),
            time = LocalTime(14, 30, 45)
        )
        val connectorText = " - "

        // When
        val result = dateTime.toFormattedString(connectorText)

        // Then
        assertEquals("15/06/2024  -  14:30:45", result)
    }

    @Test
    fun `given LocalDateTime with empty connector text when toFormattedString then formats without connector`() {
        // Given
        val dateTime = LocalDateTime(
            date = LocalDate(2024, 3, 8),
            time = LocalTime(10, 15, 30)
        )
        val connectorText = ""

        // When
        val result = dateTime.toFormattedString(connectorText)

        // Then
        assertEquals("08/03/2024  10:15:30", result)
    }

    @Test
    fun `given LocalDateTime with midnight time when toFormattedString then formats correctly`() {
        // Given
        val dateTime = LocalDateTime(
            date = LocalDate(2024, 1, 1),
            time = LocalTime(0, 0, 0)
        )
        val connectorText = "at"

        // When
        val result = dateTime.toFormattedString(connectorText)

        // Then
        assertEquals("01/01/2024 at 00:00:00", result)
    }

    @Test
    fun `given LocalDateTime with noon time when toFormattedString then formats correctly`() {
        // Given
        val dateTime = LocalDateTime(
            date = LocalDate(2024, 7, 4),
            time = LocalTime(12, 0, 0)
        )
        val connectorText = "at"

        // When
        val result = dateTime.toFormattedString(connectorText)

        // Then
        assertEquals("04/07/2024 at 12:00:00", result)
    }

    @Test
    fun `given LocalDateTime with leap year date when toFormattedString then formats correctly`() {
        // Given
        val dateTime = LocalDateTime(
            date = LocalDate(2024, 2, 29),
            time = LocalTime(16, 45, 30)
        )
        val connectorText = "at"

        // When
        val result = dateTime.toFormattedString(connectorText)

        // Then
        assertEquals("29/02/2024 at 16:45:30", result)
    }

    @Test
    fun `given LocalDateTime with year boundary when toFormattedString then formats correctly`() {
        // Given
        val dateTime = LocalDateTime(
            date = LocalDate(1999, 12, 31),
            time = LocalTime(23, 59, 59)
        )
        val connectorText = "at"

        // When
        val result = dateTime.toFormattedString(connectorText)

        // Then
        assertEquals("31/12/1999 at 23:59:59", result)
    }

    @Test
    fun `given LocalDateTime with future year when toFormattedString then formats correctly`() {
        // Given
        val dateTime = LocalDateTime(
            date = LocalDate(2030, 5, 20),
            time = LocalTime(8, 30, 15)
        )
        val connectorText = "at"

        // When
        val result = dateTime.toFormattedString(connectorText)

        // Then
        assertEquals("20/05/2030 at 08:30:15", result)
    }

    @Test
    fun `given LocalDateTime with special characters in connector when toFormattedString then formats correctly`() {
        // Given
        val dateTime = LocalDateTime(
            date = LocalDate(2024, 4, 10),
            time = LocalTime(11, 22, 33)
        )
        val connectorText = " @ "

        // When
        val result = dateTime.toFormattedString(connectorText)

        // Then
        assertEquals("10/04/2024  @  11:22:33", result)
    }

    @Test
    fun `given LocalDateTime with all single digits when toFormattedString then all components are padded`() {
        // Given
        val dateTime = LocalDateTime(
            date = LocalDate(2001, 1, 1),
            time = LocalTime(1, 1, 1)
        )
        val connectorText = "at"

        // When
        val result = dateTime.toFormattedString(connectorText)

        // Then
        assertEquals("01/01/2001 at 01:01:01", result)
    }

    @Test
    fun `given LocalDateTime with mixed digit lengths when toFormattedString then formats with proper padding`() {
        // Given
        val dateTime = LocalDateTime(
            date = LocalDate(2024, 1, 15),
            time = LocalTime(9, 30, 5)
        )
        val connectorText = "at"

        // When
        val result = dateTime.toFormattedString(connectorText)

        // Then
        assertEquals("15/01/2024 at 09:30:05", result)
    }

    @Test
    fun `given LocalDateTime with zero values when toFormattedString then formats with proper padding`() {
        // Given
        val dateTime = LocalDateTime(
            date = LocalDate(2000, 1, 1),
            time = LocalTime(0, 0, 0)
        )
        val connectorText = "at"

        // When
        val result = dateTime.toFormattedString(connectorText)

        // Then
        assertEquals("01/01/2000 at 00:00:00", result)
    }

    @Test
    fun `given LocalDateTime with maximum time values when toFormattedString then formats correctly`() {
        // Given
        val dateTime = LocalDateTime(
            date = LocalDate(2024, 12, 31),
            time = LocalTime(23, 59, 59)
        )
        val connectorText = "at"

        // When
        val result = dateTime.toFormattedString(connectorText)

        // Then
        assertEquals("31/12/2024 at 23:59:59", result)
    }

    @Test
    fun `given LocalDateTime with long connector text when toFormattedString then formats correctly`() {
        // Given
        val dateTime = LocalDateTime(
            date = LocalDate(2024, 6, 15),
            time = LocalTime(14, 30, 45)
        )
        val connectorText = " created on "

        // When
        val result = dateTime.toFormattedString(connectorText)

        // Then
        assertEquals("15/06/2024  created on  14:30:45", result)
    }
}
