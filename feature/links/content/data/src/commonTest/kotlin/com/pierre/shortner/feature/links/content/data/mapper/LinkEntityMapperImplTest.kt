package com.pierre.shortner.feature.links.content.data.mapper

import com.pierre.shortner.core.room_provider.entity.LinkEntity
import com.pierre.shortner.core.utils.date.LocalDateTimeProvider
import com.pierre.shortner.feature.links.content.domain.model.LinkDomainModel
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlin.test.Test
import kotlin.test.assertEquals

class LinkEntityMapperImplTest {

    private val fakeLocalDateTimeProvider = LocalDateTimeProvider { timestamp ->
        LocalDateTime(
            date = LocalDate(2024, 1, 15),
            time = LocalTime(10, 30, 45)
        )
    }

    private val mapper = LinkEntityMapperImpl(fakeLocalDateTimeProvider)

    @Test
    fun `given LinkEntity when map then returns correct LinkDomainModel`() {
        // Given
        val entity = LinkEntity(
            id = 1L,
            shortedLink = "https://short.ly/abc123",
            originalLink = "https://www.example.com",
            alias = "example-link",
            createdAt = 1705312245000L
        )

        // When
        val result = mapper.map(entity)

        // Then
        val expected = LinkDomainModel(
            id = 1L,
            originalUrl = "https://www.example.com",
            shortenedUrl = "https://short.ly/abc123",
            alias = "example-link",
            createdAt = LocalDateTime(
                date = LocalDate(2024, 1, 15),
                time = LocalTime(10, 30, 45)
            )
        )
        assertEquals(expected, result)
    }

    @Test
    fun `given LinkEntity with empty alias when map then returns correct LinkDomainModel`() {
        // Given
        val entity = LinkEntity(
            id = 2L,
            shortedLink = "https://short.ly/empty",
            originalLink = "https://www.google.com",
            alias = "",
            createdAt = 1705312245000L
        )

        // When
        val result = mapper.map(entity)

        // Then
        val expected = LinkDomainModel(
            id = 2L,
            originalUrl = "https://www.google.com",
            shortenedUrl = "https://short.ly/empty",
            alias = "",
            createdAt = LocalDateTime(
                date = LocalDate(2024, 1, 15),
                time = LocalTime(10, 30, 45)
            )
        )
        assertEquals(expected, result)
    }

    @Test
    fun `given LinkEntity with custom provider when map then uses provider`() {
        // Given
        val customProvider = LocalDateTimeProvider { timestamp ->
            LocalDateTime(
                date = LocalDate(2023, 12, 25),
                time = LocalTime(15, 45, 30)
            )
        }
        val customMapper = LinkEntityMapperImpl(customProvider)
        val entity = LinkEntity(
            id = 3L,
            shortedLink = "https://short.ly/custom",
            originalLink = "https://www.example.com",
            alias = "custom-time",
            createdAt = 1234567890000L
        )

        // When
        val result = customMapper.map(entity)

        // Then
        val expected = LinkDomainModel(
            id = 3L,
            originalUrl = "https://www.example.com",
            shortenedUrl = "https://short.ly/custom",
            alias = "custom-time",
            createdAt = LocalDateTime(
                date = LocalDate(2023, 12, 25),
                time = LocalTime(15, 45, 30)
            )
        )
        assertEquals(expected, result)
    }
}
