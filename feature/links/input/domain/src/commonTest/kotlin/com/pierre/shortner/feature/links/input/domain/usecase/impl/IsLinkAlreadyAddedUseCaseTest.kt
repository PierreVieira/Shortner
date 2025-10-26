package com.pierre.shortner.feature.links.input.domain.usecase.impl

import com.pierre.shortner.feature.links.input.domain.repository.LinkInputRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class IsLinkAlreadyAddedUseCaseTest {

    private val repository = MockLinkInputRepository()
    private val useCase = IsLinkAlreadyAddedUseCase(repository)

    @Test
    fun `given existing URL when invoke then returns true`() = runTest {
        // Given
        val existingUrl = "https://example.com"
        repository.allOriginalLinks = listOf(
            "https://google.com",
            existingUrl,
            "https://github.com"
        )

        // When
        val result = useCase(existingUrl)

        // Then
        assertTrue(result, "Expected true for existing URL")
    }

    @Test
    fun `given non-existing URL when invoke then returns false`() = runTest {
        // Given
        val nonExistingUrl = "https://nonexistent.com"
        repository.allOriginalLinks = listOf(
            "https://google.com",
            "https://example.com",
            "https://github.com"
        )

        // When
        val result = useCase(nonExistingUrl)

        // Then
        assertFalse(result, "Expected false for non-existing URL")
    }

    private class MockLinkInputRepository : LinkInputRepository {
        var allOriginalLinks: List<String> = emptyList()

        override suspend fun getAllOriginalLinks(): List<String> = allOriginalLinks

        override suspend fun postUrl(url: String): Result<Unit> {
            throw NotImplementedError("Not used in these tests")
        }
    }
}
