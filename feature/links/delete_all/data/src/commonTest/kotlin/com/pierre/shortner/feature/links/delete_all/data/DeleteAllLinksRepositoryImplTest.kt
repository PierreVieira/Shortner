package com.pierre.shortner.feature.links.delete_all.data

import com.pierre.shortner.core.room_provider.dao.LinkDao
import com.pierre.shortner.core.room_provider.entity.LinkEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DeleteAllLinksRepositoryImplTest {

    private val fakeDao = FakeLinkDao()
    private val repository = DeleteAllLinksRepositoryImpl(fakeDao)

    @Test
    fun `given repository when clear then calls dao clear`() = runTest {
        // Given
        val fakeDao = FakeLinkDao()
        val repository = DeleteAllLinksRepositoryImpl(fakeDao)

        // When
        repository.clear()

        // Then
        assertEquals(1, fakeDao.clearCallCount)
    }

    @Test
    fun `given repository when clear called multiple times then calls dao clear each time`() = runTest {
        // When
        val amountOfCalls = 3
        repeat(amountOfCalls) {
            repository.clear()
        }

        // Then
        assertEquals(amountOfCalls, fakeDao.clearCallCount)
    }

    private class FakeLinkDao : LinkDao {
        var clearCallCount = 0

        override fun getAllAsFlow(): Flow<List<LinkEntity>> = flowOf(emptyList())
        override suspend fun getAll(): List<LinkEntity> = emptyList()
        override suspend fun getById(id: Long): LinkEntity? = null
        override suspend fun getByShortedLink(shortedLink: String): LinkEntity? = null
        override suspend fun getByOriginalLink(originalLink: String): LinkEntity? = null
        override suspend fun insert(entity: LinkEntity): Long = 1L
        override suspend fun update(entity: LinkEntity) {}
        override suspend fun deleteById(id: Long) {}
        override suspend fun deleteByShortedLink(shortedLink: String) {}
        override suspend fun clear() {
            clearCallCount++
        }
    }
}
