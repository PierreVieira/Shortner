package com.pierre.shortner.feature.links.top_bar.data.repository

import com.pierre.shortner.core.room_provider.dao.LinkDao
import com.pierre.shortner.core.room_provider.entity.LinkEntity
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ShortenerTopBarRepositoryImplTest {

    @Test
    fun `given links when getLinks then returns list of link IDs`() = runTest {
        // Given
        val linkEntities = listOf(
            LinkEntity(
                id = 1L,
                shortedLink = "short1",
                originalLink = "original1",
                alias = "alias1",
                createdAt = 1000L
            ),
            LinkEntity(
                id = 2L,
                shortedLink = "short2",
                originalLink = "original2",
                alias = "alias2",
                createdAt = 2000L
            ),
            LinkEntity(
                id = 3L,
                shortedLink = "short3",
                originalLink = "original3",
                alias = "alias3",
                createdAt = 3000L
            )
        )
        val mockDao = MockLinkDao()
        mockDao.getAllResult = linkEntities
        val repository = ShortenerTopBarRepositoryImpl(mockDao)

        // When
        val result = repository.getLinks()

        // Then
        assertEquals(listOf(1L, 2L, 3L), result)
    }

    @Test
    fun `given empty links when getLinks then returns empty list`() = runTest {
        // Given
        val mockDao = MockLinkDao()
        mockDao.getAllResult = emptyList()
        val repository = ShortenerTopBarRepositoryImpl(mockDao)

        // When
        val result = repository.getLinks()

        // Then
        assertEquals(emptyList(), result)
    }

    private class MockLinkDao : LinkDao {
        var getAllResult: List<LinkEntity> = emptyList()

        override suspend fun getAll(): List<LinkEntity> = getAllResult

        override fun getAllAsFlow(): kotlinx.coroutines.flow.Flow<List<LinkEntity>> {
            throw NotImplementedError("Not used in these tests")
        }

        override suspend fun getById(id: Long): LinkEntity? {
            throw NotImplementedError("Not used in these tests")
        }

        override suspend fun getByShortedLink(shortedLink: String): LinkEntity? {
            throw NotImplementedError("Not used in these tests")
        }

        override suspend fun getByOriginalLink(originalLink: String): LinkEntity? {
            throw NotImplementedError("Not used in these tests")
        }

        override suspend fun insert(entity: LinkEntity): Long {
            throw NotImplementedError("Not used in these tests")
        }

        override suspend fun update(entity: LinkEntity) {
            throw NotImplementedError("Not used in these tests")
        }

        override suspend fun deleteById(id: Long) {
            throw NotImplementedError("Not used in these tests")
        }

        override suspend fun deleteByShortedLink(shortedLink: String) {
            throw NotImplementedError("Not used in these tests")
        }

        override suspend fun clear() {
            throw NotImplementedError("Not used in these tests")
        }
    }
}
