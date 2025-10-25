package com.pierre.shortner.feature.links.content.presentation.mapper

import com.pierre.shortner.feature.links.content.domain.model.LinkDomainModel
import com.pierre.shortner.feature.links.content.presentation.model.LinkPresentationModel
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlin.test.Test
import kotlin.test.assertEquals

class LinkModelMapperImplTest {

    private val mapper = LinkModelMapperImpl { "at" }

    @Test
    fun `given LinkDomainModel when toPresentation then returns correct LinkPresentationModel`() =
        runTest {
            // Given
            val domainModel = LinkDomainModel(
                id = 1L,
                originalUrl = "https://www.example.com",
                shortenedUrl = "https://short.ly/abc123",
                alias = "example-link",
                createdAt = LocalDateTime(
                    date = LocalDate(2024, 1, 15),
                    time = LocalTime(10, 30, 45)
                )
            )

            // When
            val result = mapper.toPresentation(domainModel)

            // Then
            val expected = LinkPresentationModel(
                id = 1L,
                originalUrl = "https://www.example.com",
                shortenedUrl = "https://short.ly/abc123",
                alias = "example-link",
                createdAt = "15/01/2024 at 10:30:45",
                isCardExpanded = false,
                isMenuExpanded = false
            )
            assertEquals(expected, result)
        }

    @Test
    fun `given LinkDomainModel with empty alias when toPresentation then returns correct LinkPresentationModel`() =
        runTest {
            // Given
            val domainModel = LinkDomainModel(
                id = 2L,
                originalUrl = "https://www.google.com",
                shortenedUrl = "https://s.ly/xyz789",
                alias = "",
                createdAt = LocalDateTime(
                    date = LocalDate(2024, 12, 31),
                    time = LocalTime(23, 59, 59)
                )
            )

            // When
            val result = mapper.toPresentation(domainModel)

            // Then
            val expected = LinkPresentationModel(
                id = 2L,
                originalUrl = "https://www.google.com",
                shortenedUrl = "https://s.ly/xyz789",
                alias = "",
                createdAt = "31/12/2024 at 23:59:59",
                isCardExpanded = false,
                isMenuExpanded = false
            )
            assertEquals(expected, result)
        }

    @Test
    fun `given LinkDomainModel with long URLs when toPresentation then returns correct LinkPresentationModel`() =
        runTest {
            // Given
            val domainModel = LinkDomainModel(
                id = 3L,
                originalUrl = "https://www.very-long-domain-name.com/very/long/path/to/some/resource?param1=value1&param2=value2",
                shortenedUrl = "https://tiny.url/abc123def456",
                alias = "long-url-alias",
                createdAt = LocalDateTime(
                    date = LocalDate(2024, 6, 1),
                    time = LocalTime(0, 0, 0)
                )
            )

            // When
            val result = mapper.toPresentation(domainModel)

            // Then
            val expected = LinkPresentationModel(
                id = 3L,
                originalUrl = "https://www.very-long-domain-name.com/very/long/path/to/some/resource?param1=value1&param2=value2",
                shortenedUrl = "https://tiny.url/abc123def456",
                alias = "long-url-alias",
                createdAt = "01/06/2024 at 00:00:00",
                isCardExpanded = false,
                isMenuExpanded = false
            )
            assertEquals(expected, result)
        }
}
