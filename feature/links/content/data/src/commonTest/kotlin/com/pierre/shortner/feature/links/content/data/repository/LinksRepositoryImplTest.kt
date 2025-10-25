package com.pierre.shortner.feature.links.content.data.repository

import com.pierre.shortner.core.room_provider.dao.LinkDao
import com.pierre.shortner.core.room_provider.entity.LinkEntity
import com.pierre.shortner.feature.links.content.data.mapper.LinkEntityMapper
import com.pierre.shortner.feature.links.content.domain.model.LinkDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlin.test.Test
import kotlin.test.assertEquals

class LinksRepositoryImplTest {

    private val fakeLinkDao = FakeLinkDao(
        listOf(
            LinkEntity(
                id = 1L,
                shortedLink = "https://short.ly/abc123",
                originalLink = "https://www.example.com",
                alias = "example-link",
                createdAt = 1705312245000L
            ),
            LinkEntity(
                id = 2L,
                shortedLink = "https://short.ly/xyz789",
                originalLink = "https://www.google.com",
                alias = "google-link",
                createdAt = 1705312245000L
            )
        )
    )

    private val fakeMapper = LinkEntityMapper { entity ->
        LinkDomainModel(
            id = entity.id,
            originalUrl = entity.originalLink,
            shortenedUrl = entity.shortedLink,
            alias = entity.alias,
            createdAt = LocalDateTime(
                date = LocalDate(2024, 1, 15),
                time = LocalTime(10, 30, 45)
            )
        )
    }

    private val repository = LinksRepositoryImpl(fakeLinkDao, fakeMapper)

    @Test
    fun `given repository when getAllLinks then returns mapped domain models`() = runTest {
        // Given
        val expected = listOf(
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

        // When
        val result = repository.getAllLinks()

        // Then
        val actual = result.toList()
        assertEquals(listOf(expected), actual)
    }

    @Test
    fun `given empty dao when getAllLinks then returns empty list`() = runTest {
        // Given
        val emptyDao = FakeLinkDao(emptyList())
        val emptyRepository = LinksRepositoryImpl(emptyDao, fakeMapper)

        // When
        val result = emptyRepository.getAllLinks()

        // Then
        val actual = result.toList()
        assertEquals(listOf(emptyList()), actual)
    }

    @Test
    fun `given single entity when getAllLinks then returns single mapped model`() = runTest {
        // Given
        val singleEntityDao = FakeLinkDao(
            listOf(
                LinkEntity(
                    id = 1L,
                    shortedLink = "https://short.ly/single",
                    originalLink = "https://www.single.com",
                    alias = "single-link",
                    createdAt = 1705312245000L
                )
            )
        )
        val singleRepository = LinksRepositoryImpl(singleEntityDao, fakeMapper)
        val expected = listOf(
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

        // When
        val result = singleRepository.getAllLinks()

        // Then
        val actual = result.toList()
        assertEquals(listOf(expected), actual)
    }

    private class FakeLinkDao(
        private val entities: List<LinkEntity>
    ) : LinkDao {
        override fun getAllAsFlow(): Flow<List<LinkEntity>> = flowOf(entities)
        override suspend fun getAll(): List<LinkEntity> = emptyList()
        override suspend fun getById(id: Long): LinkEntity? = null
        override suspend fun getByShortedLink(shortedLink: String): LinkEntity? = null
        override suspend fun getByOriginalLink(originalLink: String): LinkEntity? = null
        override suspend fun insert(entity: LinkEntity): Long = 0L
        override suspend fun update(entity: LinkEntity) {}
        override suspend fun deleteById(id: Long) {}
        override suspend fun deleteByShortedLink(shortedLink: String) {}
        override suspend fun clear() {}
    }
}
