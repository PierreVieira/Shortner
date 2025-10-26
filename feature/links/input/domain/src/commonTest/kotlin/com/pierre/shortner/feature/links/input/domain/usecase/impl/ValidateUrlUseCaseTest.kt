package com.pierre.shortner.feature.links.input.domain.usecase.impl

import com.pierre.shortner.feature.links.input.domain.model.UrlValidationException
import com.pierre.shortner.feature.links.input.domain.usecase.IsLinkAlreadyAdded
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertTrue

class ValidateUrlUseCaseTest {

    private val isLinkAlreadyAdded = MockIsLinkAlreadyAdded()
    private val useCase = ValidateUrlUseCase(isLinkAlreadyAdded)

    @Test
    fun `given valid URL that does not exist when invoke then returns success`() = runTest {
        // Given
        val validUrl = "https://example.com"
        isLinkAlreadyAdded.isLinkAlreadyAddedResult = false

        // When
        val result = useCase(validUrl)

        // Then
        assertTrue(result.isSuccess, "Expected success but was $result")
    }

    @Test
    fun `given blank URL when invoke then returns failure with Empty exception`() = runTest {
        // Given
        val blankUrl = "   "

        // When
        val result = useCase(blankUrl)

        // Then
        assertTrue(result.isFailure, "Expected failure but was $result")
        assertTrue(result.exceptionOrNull() is UrlValidationException.Empty)
    }

    @Test
    fun `given URL that already exists when invoke then returns failure with LinkAlreadyAdded exception`() = runTest {
        // Given
        val existingUrl = "https://example.com"
        isLinkAlreadyAdded.isLinkAlreadyAddedResult = true

        // When
        val result = useCase(existingUrl)

        // Then
        assertTrue(result.isFailure, "Expected failure but was $result")
        assertTrue(result.exceptionOrNull() is UrlValidationException.LinkAlreadyAdded)
    }

    @Test
    fun `given invalid URL format when invoke then returns failure with Invalid exception`() = runTest {
        // Given
        val invalidUrl = "not-a-url"
        isLinkAlreadyAdded.isLinkAlreadyAddedResult = false

        // When
        val result = useCase(invalidUrl)

        // Then
        assertTrue(result.isFailure, "Expected failure but was $result")
        assertTrue(result.exceptionOrNull() is UrlValidationException.Invalid)
    }

    @Test
    fun `given HTTP URL when invoke then returns success`() = runTest {
        // Given
        val httpUrl = "http://example.com"
        isLinkAlreadyAdded.isLinkAlreadyAddedResult = false

        // When
        val result = useCase(httpUrl)

        // Then
        assertTrue(result.isSuccess, "Expected success but was $result")
    }

    @Test
    fun `given HTTPS URL when invoke then returns success`() = runTest {
        // Given
        val httpsUrl = "https://example.com"
        isLinkAlreadyAdded.isLinkAlreadyAddedResult = false

        // When
        val result = useCase(httpsUrl)

        // Then
        assertTrue(result.isSuccess, "Expected success but was $result")
    }

    private class MockIsLinkAlreadyAdded : IsLinkAlreadyAdded {
        var isLinkAlreadyAddedResult: Boolean = false
        var lastCheckedUrl: String? = null

        override suspend fun invoke(originalUrl: String): Boolean {
            lastCheckedUrl = originalUrl
            return isLinkAlreadyAddedResult
        }
    }
}
