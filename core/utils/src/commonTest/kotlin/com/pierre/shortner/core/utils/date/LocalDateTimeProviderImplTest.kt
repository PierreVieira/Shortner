package com.pierre.shortner.core.utils.date

import kotlinx.datetime.number
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class LocalDateTimeProviderImplTest {

    private val provider = LocalDateTimeProviderImpl()

    @Test
    fun `given epoch timestamp when getLocalDateTime then returns valid LocalDateTime`() {
        // Given
        val epochTimestamp = 0L

        // When
        val result = provider.getLocalDateTime(epochTimestamp)

        // Then
        assertNotNull(result)
        assertTrue(result.date.year >= 1969)
        assertTrue(result.date.year <= 1971)
        assertTrue(result.date.month.number >= 1)
        assertTrue(result.date.month.number <= 12)
        assertTrue(result.date.day >= 1)
        assertTrue(result.date.day <= 31)
        assertTrue(result.time.hour >= 0)
        assertTrue(result.time.hour <= 23)
        assertTrue(result.time.minute >= 0)
        assertTrue(result.time.minute <= 59)
        assertTrue(result.time.second >= 0)
        assertTrue(result.time.second <= 59)
    }

    @Test
    fun `given current timestamp when getLocalDateTime then returns valid LocalDateTime`() {
        // Given
        // Use a fixed timestamp for 2024-01-01 12:00:00 UTC
        val currentTimestamp = 1704110400000L

        // When
        val result = provider.getLocalDateTime(currentTimestamp)

        // Then
        assertNotNull(result)
        assertTrue(result.date.year >= 2020)
        assertTrue(result.date.year <= 2030)
    }

    @Test
    fun `given future timestamp when getLocalDateTime then returns valid LocalDateTime`() {
        // Given
        // Use a fixed timestamp for 2025-01-01 12:00:00 UTC
        val futureTimestamp = 1735723200000L

        // When
        val result = provider.getLocalDateTime(futureTimestamp)

        // Then
        assertNotNull(result)
        assertTrue(result.date.year >= 2024)
    }

    @Test
    fun `given past timestamp when getLocalDateTime then returns valid LocalDateTime`() {
        // Given
        // Use a fixed timestamp for 2023-01-01 12:00:00 UTC
        val pastTimestamp = 1672574400000L

        // When
        val result = provider.getLocalDateTime(pastTimestamp)

        // Then
        assertNotNull(result)
        assertTrue(result.date.year >= 2020)
    }

    @Test
    fun `given same timestamp when getLocalDateTime called multiple times then returns same result`() {
        // Given
        val timestamp = 1704067200000L

        // When
        val result1 = provider.getLocalDateTime(timestamp)
        val result2 = provider.getLocalDateTime(timestamp)

        // Then
        assertEquals(result1.date.year, result2.date.year)
        assertEquals(result1.date.month, result2.date.month)
        assertEquals(result1.date.day, result2.date.day)
        assertEquals(result1.time.hour, result2.time.hour)
        assertEquals(result1.time.minute, result2.time.minute)
        assertEquals(result1.time.second, result2.time.second)
    }

    @Test
    fun `given different timestamps when getLocalDateTime then returns different results`() {
        // Given
        val timestamp1 = 1704067200000L
        val timestamp2 = 1704153600000L // 1 day later

        // When
        val result1 = provider.getLocalDateTime(timestamp1)
        val result2 = provider.getLocalDateTime(timestamp2)

        // Then
        assertTrue(result1.date.day != result2.date.day || result1.date.month != result2.date.month || result1.date.year != result2.date.year)
    }

    @Test
    fun `given timestamp for year 2000 when getLocalDateTime then returns year 2000`() {
        // Given
        // Approximate timestamp for year 2000
        val timestamp = 946684800000L

        // When
        val result = provider.getLocalDateTime(timestamp)

        // Then
        assertTrue(result.date.year >= 1999)
        assertTrue(result.date.year <= 2001)
    }

    @Test
    fun `given timestamp for year 2030 when getLocalDateTime then returns year 2030`() {
        // Given
        // Approximate timestamp for year 2030
        val timestamp = 1893456000000L

        // When
        val result = provider.getLocalDateTime(timestamp)

        // Then
        assertTrue(result.date.year >= 2029)
        assertTrue(result.date.year <= 2031)
    }

    @Test
    fun `given negative timestamp when getLocalDateTime then returns valid LocalDateTime`() {
        // Given
        val negativeTimestamp = -1000L

        // When
        val result = provider.getLocalDateTime(negativeTimestamp)

        // Then
        assertNotNull(result)
        assertTrue(result.date.year < 1970)
    }

    @Test
    fun `given very large timestamp when getLocalDateTime then returns valid LocalDateTime`() {
        // Given
        val largeTimestamp = Long.MAX_VALUE

        // When
        val result = provider.getLocalDateTime(largeTimestamp)

        // Then
        assertNotNull(result)
        assertTrue(result.date.year > 2000)
    }

    @Test
    fun `given zero timestamp when getLocalDateTime then returns valid LocalDateTime`() {
        // Given
        val zeroTimestamp = 0L

        // When
        val result = provider.getLocalDateTime(zeroTimestamp)

        // Then
        assertNotNull(result)
        assertTrue(result.date.year >= 1969)
        assertTrue(result.date.year <= 1971)
    }

    @Test
    fun `given timestamp with milliseconds when getLocalDateTime then returns valid LocalDateTime`() {
        // Given
        val timestampWithMs = 1704067200123L

        // When
        val result = provider.getLocalDateTime(timestampWithMs)

        // Then
        assertNotNull(result)
        assertTrue(result.date.year >= 2020)
    }

    @Test
    fun `given timestamp for leap year when getLocalDateTime then returns valid LocalDateTime`() {
        // Given
        // Approximate timestamp for 2024 (leap year)
        val leapYearTimestamp = 1704067200000L

        // When
        val result = provider.getLocalDateTime(leapYearTimestamp)

        // Then
        assertNotNull(result)
        assertTrue(result.date.year >= 2020)
        assertTrue(result.date.year <= 2030)
    }

    @Test
    fun `given timestamp for February when getLocalDateTime then returns valid LocalDateTime`() {
        // Given
        // Approximate timestamp for February
        val februaryTimestamp = 1706745600000L

        // When
        val result = provider.getLocalDateTime(februaryTimestamp)

        // Then
        assertNotNull(result)
        assertTrue(result.date.month.number >= 1)
        assertTrue(result.date.month.number <= 12)
    }

    @Test
    fun `given timestamp for December when getLocalDateTime then returns valid LocalDateTime`() {
        // Given
        // Approximate timestamp for December
        val decemberTimestamp = 1733011200000L

        // When
        val result = provider.getLocalDateTime(decemberTimestamp)

        // Then
        assertNotNull(result)
        assertTrue(result.date.month.number >= 1)
        assertTrue(result.date.month.number <= 12)
    }
}
