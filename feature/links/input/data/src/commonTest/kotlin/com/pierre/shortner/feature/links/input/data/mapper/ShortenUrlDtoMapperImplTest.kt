package com.pierre.shortner.feature.links.input.data.mapper

import com.pierre.shortner.core.room_provider.entity.LinkEntity
import com.pierre.shortner.core.utils.time.CurrentTimeProvider
import com.pierre.shortner.feature.links.input.data.dto.LinksDto
import com.pierre.shortner.feature.links.input.data.dto.ShortenUrlDto
import kotlin.test.Test
import kotlin.test.assertEquals

class ShortenUrlDtoMapperImplTest {

    private val currentTimeProvider = MockCurrentTimeProvider()
    private val mapper = ShortenUrlDtoMapperImpl(currentTimeProvider)

    @Test
    fun `given ShortenUrlDto when toEntity then returns correct LinkEntity`() {
        // Given
        val testTimestamp = 1234567890L
        val shortenUrlDto = ShortenUrlDto(
            alias = "abc123",
            linksDto = LinksDto(
                original = "https://example.com",
                short = "https://short.ly/abc123"
            )
        )
        val expectedEntity = LinkEntity(
            id = 0,
            originalLink = "https://example.com",
            shortedLink = "https://short.ly/abc123",
            alias = "abc123",
            createdAt = testTimestamp
        )

        currentTimeProvider.currentTimestamp = testTimestamp

        // When
        val result = mapper.toEntity(shortenUrlDto)

        // Then
        assertEquals(expectedEntity, result)
    }

    @Test
    fun `given ShortenUrlDto when toEntity then uses current time provider timestamp`() {
        // Given
        val testTimestamp = 9876543210L
        val shortenUrlDto = ShortenUrlDto(
            alias = "test",
            linksDto = LinksDto(
                original = "https://test.com",
                short = "https://short.ly/test"
            )
        )

        currentTimeProvider.currentTimestamp = testTimestamp

        // When
        val result = mapper.toEntity(shortenUrlDto)

        // Then
        assertEquals(testTimestamp, result.createdAt)
    }

    private class MockCurrentTimeProvider : CurrentTimeProvider {
        var currentTimestamp: Long = 0L

        override fun getCurrentTimeStamp(): Long = currentTimestamp
    }
}