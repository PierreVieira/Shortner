package com.pierre.shortner.feature.links.input.domain.usecase.impl

import com.pierre.shortner.feature.links.input.domain.model.ShortenUrlStep
import com.pierre.shortner.feature.links.input.domain.repository.LinkInputRepository
import com.pierre.shortner.feature.links.input.domain.usecase.IsLinkAlreadyAdded
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ShortenUrlUseCaseTest {

    private val isLinkAlreadyAdded = MockIsLinkAlreadyAdded()
    private val validateUrl = ValidateUrlUseCase(isLinkAlreadyAdded)
    private val repository = MockLinkInputRepository()
    private val useCase = ShortenUrlUseCase(validateUrl, repository)

    @Test
    fun `given valid URL when invoke then emits InProgress then Success`() = runTest {
        // Given
        val validUrl = "https://example.com"
        isLinkAlreadyAdded.isLinkAlreadyAddedResult = false
        repository.postUrlResult = Result.success(Unit)

        // When
        val result = useCase(validUrl).toList()

        // Then
        assertEquals(2, result.size)
        assertTrue(result[0] is ShortenUrlStep.InProgress)
        assertTrue(result[1] is ShortenUrlStep.Success)
    }

    @Test
    fun `given invalid URL when invoke then emits Error`() = runTest {
        // Given
        val invalidUrl = "invalid-url"
        isLinkAlreadyAdded.isLinkAlreadyAddedResult = false

        // When
        val result = useCase(invalidUrl).toList()

        // Then
        assertEquals(1, result.size)
        assertTrue(result[0] is ShortenUrlStep.Error)
    }

    @Test
    fun `given valid URL but repository fails when invoke then emits InProgress then Error`() = runTest {
        // Given
        val validUrl = "https://example.com"
        val repositoryException = RuntimeException("Network error")
        isLinkAlreadyAdded.isLinkAlreadyAddedResult = false
        repository.postUrlResult = Result.failure(repositoryException)

        // When
        val result = useCase(validUrl).toList()

        // Then
        assertEquals(2, result.size)
        assertTrue(result[0] is ShortenUrlStep.InProgress)
        assertTrue(result[1] is ShortenUrlStep.Error)
        assertEquals(repositoryException, (result[1] as ShortenUrlStep.Error).throwable)
    }

    @Test
    fun `given URL with whitespace when invoke then trims URL before validation`() = runTest {
        // Given
        val urlWithWhitespace = "  https://example.com  "
        val trimmedUrl = "https://example.com"
        isLinkAlreadyAdded.isLinkAlreadyAddedResult = false
        repository.postUrlResult = Result.success(Unit)

        // When
        val result = useCase(urlWithWhitespace).toList()

        // Then
        assertEquals(2, result.size)
        assertTrue(result[0] is ShortenUrlStep.InProgress)
        assertTrue(result[1] is ShortenUrlStep.Success)
        assertEquals(trimmedUrl, isLinkAlreadyAdded.lastCheckedUrl)
        assertEquals(trimmedUrl, repository.lastPostUrl)
    }

    private class MockIsLinkAlreadyAdded : IsLinkAlreadyAdded {
        var isLinkAlreadyAddedResult: Boolean = false
        var lastCheckedUrl: String? = null

        override suspend fun invoke(originalUrl: String): Boolean {
            lastCheckedUrl = originalUrl
            return isLinkAlreadyAddedResult
        }
    }

    private class MockLinkInputRepository : LinkInputRepository {
        var postUrlResult: Result<Unit> = Result.success(Unit)
        var lastPostUrl: String? = null

        override suspend fun postUrl(url: String): Result<Unit> {
            lastPostUrl = url
            return postUrlResult
        }

        override suspend fun getAllOriginalLinks(): List<String> {
            throw NotImplementedError("Not used in these tests")
        }
    }
}
