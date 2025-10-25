package com.pierre.shortner.feature.links.delete_link.data.repository

import com.pierre.shortner.core.room_provider.dao.LinkDao
import com.pierre.shortner.core.room_provider.entity.LinkEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DeleteLinkRepositoryImplTest {

    private val fakeDao = FakeLinkDao()
    private val repository = DeleteLinkRepositoryImpl(fakeDao)

    @Test
    fun `given repository when deleteLink then calls dao deleteById`() = runTest {
        // Given
        val linkId = 123L

        // When
        repository.deleteLink(linkId)

        // Then
        assertEquals(linkId, fakeDao.deletedId)
        assertEquals(1, fakeDao.deleteCallCount)
    }

    @Test
    fun `given repository when deleteLink called multiple times then calls dao deleteById each time`() = runTest {
        // Given
        val linkIds = listOf(1L, 2L, 3L)

        // When
        linkIds.forEach { id ->
            repository.deleteLink(id)
        }

        // Then
        assertEquals(3, fakeDao.deleteCallCount)
        assertEquals(3L, fakeDao.deletedId) // Last deleted ID
    }

    @Test
    fun `given repository when deleteLink with zero id then calls dao deleteById`() = runTest {
        // Given
        val linkId = 0L

        // When
        repository.deleteLink(linkId)

        // Then
        assertEquals(linkId, fakeDao.deletedId)
        assertEquals(1, fakeDao.deleteCallCount)
    }

    @Test
    fun `given repository when deleteLink with negative id then calls dao deleteById`() = runTest {
        // Given
        val linkId = -1L

        // When
        repository.deleteLink(linkId)

        // Then
        assertEquals(linkId, fakeDao.deletedId)
        assertEquals(1, fakeDao.deleteCallCount)
    }

    private class FakeLinkDao : LinkDao {
        var deleteCallCount = 0
        var deletedId: Long? = null

        override fun getAllAsFlow(): Flow<List<LinkEntity>> = flowOf(emptyList())
        override suspend fun getAll(): List<LinkEntity> = emptyList()
        override suspend fun getById(id: Long): LinkEntity? = null
        override suspend fun getByShortedLink(shortedLink: String): LinkEntity? = null
        override suspend fun getByOriginalLink(originalLink: String): LinkEntity? = null
        override suspend fun insert(entity: LinkEntity): Long = 1L
        override suspend fun update(entity: LinkEntity) {}
        override suspend fun deleteById(id: Long) {
            deleteCallCount++
            deletedId = id
        }
        override suspend fun deleteByShortedLink(shortedLink: String) {}
        override suspend fun clear() {}
    }
}
