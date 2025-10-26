package com.pierre.shortner.feature.links.input.data.repository

import com.pierre.shortner.core.room_provider.dao.LinkDao
import com.pierre.shortner.core.room_provider.entity.LinkEntity
import com.pierre.shortner.feature.links.input.data.dto.LinksDto
import com.pierre.shortner.feature.links.input.data.dto.ShortenUrlDto
import com.pierre.shortner.feature.links.input.data.mapper.ShortenUrlDtoMapper
import com.pierre.shortner.feature.links.input.data.repository.datasource.LinkInputRemoteDataSource
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class LinkInputRepositoryImplTest {

    private val remoteDataSource = MockLinkInputRemoteDataSource()
    private val linkDao = MockLinkDao()
    private val shortenUrlDtoMapper = MockShortenUrlDtoMapper()
    
    private val repository = LinkInputRepositoryImpl(
        remoteDataSource = remoteDataSource,
        linkDao = linkDao,
        shortenUrlDtoMapper = shortenUrlDtoMapper
    )

    @Test
    fun `given valid URL when postUrl then returns success and saves to database`() = runTest {
        // Given
        val testUrl = "https://example.com"
        val shortenUrlDto = ShortenUrlDto(
            alias = "abc123",
            linksDto = LinksDto(
                original = testUrl,
                short = "https://short.ly/abc123"
            )
        )
        val linkEntity = LinkEntity(
            id = 0,
            shortedLink = "https://short.ly/abc123",
            originalLink = testUrl,
            alias = "abc123",
            createdAt = 1234567890L
        )

        remoteDataSource.postUrlResult = Result.success(shortenUrlDto)
        shortenUrlDtoMapper.toEntityResult = linkEntity

        // When
        val result = repository.postUrl(testUrl)

        // Then
        assertTrue(result.isSuccess, "Expected success but was $result")
        assertEquals(1, remoteDataSource.postUrlCallCount)
        assertEquals(testUrl, remoteDataSource.lastPostUrlCall)
        assertEquals(1, shortenUrlDtoMapper.toEntityCallCount)
        assertEquals(shortenUrlDto, shortenUrlDtoMapper.lastToEntityCall)
        assertEquals(1, linkDao.insertCallCount)
        assertEquals(linkEntity, linkDao.lastInsertCall)
    }

    @Test
    fun `given remote data source failure when postUrl then returns failure`() = runTest {
        // Given
        val testUrl = "https://example.com"
        val expectedException = RuntimeException("Network error")

        remoteDataSource.postUrlResult = Result.failure(expectedException)

        // When
        val result = repository.postUrl(testUrl)

        // Then
        assertTrue(result.isFailure, "Expected failure but was $result")
        assertEquals(expectedException, result.exceptionOrNull())
        assertEquals(1, remoteDataSource.postUrlCallCount)
        assertEquals(testUrl, remoteDataSource.lastPostUrlCall)
        assertEquals(0, linkDao.insertCallCount)
    }

    @Test
    fun `given database insert failure when postUrl then propagates exception`() = runTest {
        // Given
        val testUrl = "https://example.com"
        val shortenUrlDto = ShortenUrlDto(
            alias = "abc123",
            linksDto = LinksDto(
                original = testUrl,
                short = "https://short.ly/abc123"
            )
        )
        val linkEntity = LinkEntity(
            id = 0,
            shortedLink = "https://short.ly/abc123",
            originalLink = testUrl,
            alias = "abc123",
            createdAt = 1234567890L
        )
        val databaseException = RuntimeException("Database error")

        remoteDataSource.postUrlResult = Result.success(shortenUrlDto)
        shortenUrlDtoMapper.toEntityResult = linkEntity
        linkDao.insertShouldThrow = databaseException

        // When & Then
        try {
            repository.postUrl(testUrl)
            assertTrue(false, "Expected exception to be thrown")
        } catch (e: RuntimeException) {
            assertEquals(databaseException, e)
            assertEquals(1, remoteDataSource.postUrlCallCount)
            assertEquals(1, linkDao.insertCallCount)
        }
    }

    @Test
    fun `given empty URL when postUrl then processes successfully`() = runTest {
        // Given
        val testUrl = ""
        val shortenUrlDto = ShortenUrlDto(
            alias = "empty",
            linksDto = LinksDto(
                original = testUrl,
                short = "https://short.ly/empty"
            )
        )
        val linkEntity = LinkEntity(
            id = 0,
            shortedLink = "https://short.ly/empty",
            originalLink = testUrl,
            alias = "empty",
            createdAt = 1234567890L
        )

        remoteDataSource.postUrlResult = Result.success(shortenUrlDto)
        shortenUrlDtoMapper.toEntityResult = linkEntity

        // When
        val result = repository.postUrl(testUrl)

        // Then
        assertTrue(result.isSuccess, "Expected success but was $result")
        assertEquals(1, remoteDataSource.postUrlCallCount)
        assertEquals(testUrl, remoteDataSource.lastPostUrlCall)
        assertEquals(1, linkDao.insertCallCount)
        assertEquals(linkEntity, linkDao.lastInsertCall)
    }

    @Test
    fun `given long URL when postUrl then processes successfully`() = runTest {
        // Given
        val testUrl = "https://example.com/" + "a".repeat(1000)
        val shortenUrlDto = ShortenUrlDto(
            alias = "long",
            linksDto = LinksDto(
                original = testUrl,
                short = "https://short.ly/long"
            )
        )
        val linkEntity = LinkEntity(
            id = 0,
            shortedLink = "https://short.ly/long",
            originalLink = testUrl,
            alias = "long",
            createdAt = 1234567890L
        )

        remoteDataSource.postUrlResult = Result.success(shortenUrlDto)
        shortenUrlDtoMapper.toEntityResult = linkEntity

        // When
        val result = repository.postUrl(testUrl)

        // Then
        assertTrue(result.isSuccess, "Expected success but was $result")
        assertEquals(1, remoteDataSource.postUrlCallCount)
        assertEquals(testUrl, remoteDataSource.lastPostUrlCall)
        assertEquals(1, linkDao.insertCallCount)
        assertEquals(linkEntity, linkDao.lastInsertCall)
    }

    @Test
    fun `when getAllOriginalLinks then returns all original links from database`() = runTest {
        // Given
        val linkEntities = listOf(
            LinkEntity(
                id = 1,
                shortedLink = "https://short.ly/abc123",
                originalLink = "https://example.com",
                alias = "abc123",
                createdAt = 1234567890L
            ),
            LinkEntity(
                id = 2,
                shortedLink = "https://short.ly/def456",
                originalLink = "https://google.com",
                alias = "def456",
                createdAt = 1234567891L
            ),
            LinkEntity(
                id = 3,
                shortedLink = "https://short.ly/ghi789",
                originalLink = "https://github.com",
                alias = "ghi789",
                createdAt = 1234567892L
            )
        )
        val expectedOriginalLinks = listOf(
            "https://example.com",
            "https://google.com",
            "https://github.com"
        )

        linkDao.getAllResult = linkEntities

        // When
        val result = repository.getAllOriginalLinks()

        // Then
        assertEquals(expectedOriginalLinks, result)
        assertEquals(1, linkDao.getAllCallCount)
    }

    @Test
    fun `when getAllOriginalLinks with empty database then returns empty list`() = runTest {
        // Given
        linkDao.getAllResult = emptyList()

        // When
        val result = repository.getAllOriginalLinks()

        // Then
        assertTrue(result.isEmpty(), "Expected empty list but was $result")
        assertEquals(1, linkDao.getAllCallCount)
    }

    @Test
    fun `when getAllOriginalLinks with single link then returns single original link`() = runTest {
        // Given
        val linkEntity = LinkEntity(
            id = 1,
            shortedLink = "https://short.ly/single",
            originalLink = "https://single.com",
            alias = "single",
            createdAt = 1234567890L
        )

        linkDao.getAllResult = listOf(linkEntity)

        // When
        val result = repository.getAllOriginalLinks()

        // Then
        assertEquals(listOf("https://single.com"), result)
        assertEquals(1, linkDao.getAllCallCount)
    }

    @Test
    fun `given database error when getAllOriginalLinks then propagates exception`() = runTest {
        // Given
        val databaseException = RuntimeException("Database connection failed")
        linkDao.getAllShouldThrow = databaseException

        // When & Then
        try {
            repository.getAllOriginalLinks()
            assertTrue(false, "Expected exception to be thrown")
        } catch (e: RuntimeException) {
            assertEquals(databaseException, e)
            assertEquals(1, linkDao.getAllCallCount)
        }
    }

    private class MockLinkInputRemoteDataSource : LinkInputRemoteDataSource {
        var postUrlResult: Result<ShortenUrlDto> = Result.success(
            ShortenUrlDto(
                alias = "test",
                linksDto = LinksDto(
                    original = "https://example.com",
                    short = "https://short.ly/test"
                )
            )
        )
        var postUrlCallCount = 0
        var lastPostUrlCall: String? = null

        override suspend fun postUrl(url: String): Result<ShortenUrlDto> {
            postUrlCallCount++
            lastPostUrlCall = url
            return postUrlResult
        }
    }

    private class MockLinkDao : LinkDao {
        var insertCallCount = 0
        var lastInsertCall: LinkEntity? = null
        var insertShouldThrow: Throwable? = null
        var insertResult: Long = 1L
        var getAllResult: List<LinkEntity> = emptyList()
        var getAllCallCount = 0
        var getAllShouldThrow: Throwable? = null

        override fun getAllAsFlow(): kotlinx.coroutines.flow.Flow<List<LinkEntity>> {
            throw NotImplementedError("Not used in these tests")
        }

        override suspend fun getAll(): List<LinkEntity> {
            getAllCallCount++
            getAllShouldThrow?.let { throw it }
            return getAllResult
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
            insertCallCount++
            lastInsertCall = entity
            insertShouldThrow?.let { throw it }
            return insertResult
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

    private class MockShortenUrlDtoMapper : ShortenUrlDtoMapper {
        var toEntityCallCount = 0
        var lastToEntityCall: ShortenUrlDto? = null
        var toEntityResult: LinkEntity = LinkEntity(
            id = 0,
            shortedLink = "https://short.ly/test",
            originalLink = "https://example.com",
            alias = "test",
            createdAt = 1234567890L
        )

        override fun toEntity(dto: ShortenUrlDto): LinkEntity {
            toEntityCallCount++
            lastToEntityCall = dto
            return toEntityResult
        }
    }
}