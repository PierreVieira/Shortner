package com.pierre.shortner.feature.links.top_bar.domain.usecase

import com.pierre.shortner.feature.links.top_bar.domain.repository.ShortenerTopBarRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ShouldShowDeleteAllDialogUseCaseTest {

    @Test
    fun `given non-empty links when invoke then returns true`() = runTest {
        // Given
        val repository = MockShortenerTopBarRepository()
        repository.getLinksResult = listOf(1L, 2L, 3L)
        val useCase = ShouldShowDeleteAllDialogUseCase(repository)

        // When
        val result = useCase()

        // Then
        assertTrue(result)
    }

    @Test
    fun `given empty links when invoke then returns false`() = runTest {
        // Given
        val repository = MockShortenerTopBarRepository()
        repository.getLinksResult = emptyList()
        val useCase = ShouldShowDeleteAllDialogUseCase(repository)

        // When
        val result = useCase()

        // Then
        assertFalse(result)
    }

    private class MockShortenerTopBarRepository : ShortenerTopBarRepository {
        var getLinksResult: List<Long> = emptyList()

        override suspend fun getLinks(): List<Long> = getLinksResult
    }
}
