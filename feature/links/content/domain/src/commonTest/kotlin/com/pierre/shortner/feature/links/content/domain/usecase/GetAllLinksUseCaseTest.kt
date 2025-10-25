package com.pierre.shortner.feature.links.content.domain.usecase

import com.pierre.shortner.feature.links.content.domain.model.LinkDomainModel
import com.pierre.shortner.feature.links.content.domain.repository.LinksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlin.test.Test
import kotlin.test.assertEquals

class GetAllLinksUseCaseTest {

    @Test
    fun `given use case when invoke then returns links from repository`() = runTest {
        // Given
        val expectedLinks = listOf(
            LinkDomainModel(
                id = 1L,
                originalUrl = "https://www.example.com",
                shortenedUrl = "https://short.ly/abc123",
                alias = "example-link",
                createdAt = LocalDateTime(
                    date = LocalDate(2024, 1, 15),
                    time = LocalTime(10, 30, 45)
                )
            ),
            LinkDomainModel(
                id = 2L,
                originalUrl = "https://www.google.com",
                shortenedUrl = "https://short.ly/xyz789",
                alias = "google-link",
                createdAt = LocalDateTime(
                    date = LocalDate(2024, 1, 15),
                    time = LocalTime(10, 30, 45)
                )
            )
        )
        val fakeRepository = FakeLinksRepository(expectedLinks)
        val useCase = GetAllLinksUseCase(fakeRepository)

        // When
        val result = useCase()

        // Then
        val actual = result.toList()
        assertEquals(listOf(expectedLinks), actual)
    }

    @Test
    fun `given empty repository when invoke then returns empty list`() = runTest {
        // Given
        val fakeRepository = FakeLinksRepository(emptyList())
        val useCase = GetAllLinksUseCase(fakeRepository)

        // When
        val result = useCase()

        // Then
        val actual = result.toList()
        assertEquals(listOf(emptyList()), actual)
    }

    @Test
    fun `given single link when invoke then returns single link`() = runTest {
        // Given
        val singleLink = listOf(
            LinkDomainModel(
                id = 1L,
                originalUrl = "https://www.single.com",
                shortenedUrl = "https://short.ly/single",
                alias = "single-link",
                createdAt = LocalDateTime(
                    date = LocalDate(2024, 1, 15),
                    time = LocalTime(10, 30, 45)
                )
            )
        )
        val fakeRepository = FakeLinksRepository(singleLink)
        val useCase = GetAllLinksUseCase(fakeRepository)

        // When
        val result = useCase()

        // Then
        val actual = result.toList()
        assertEquals(listOf(singleLink), actual)
    }

    private class FakeLinksRepository(
        private val links: List<LinkDomainModel>
    ) : LinksRepository {
        override fun getAllLinks(): Flow<List<LinkDomainModel>> = flowOf(links)
    }
}
