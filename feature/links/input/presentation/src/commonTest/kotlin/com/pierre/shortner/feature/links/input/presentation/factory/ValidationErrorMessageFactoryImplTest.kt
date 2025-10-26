package com.pierre.shortner.feature.links.input.presentation.factory

import com.pierre.shortner.feature.links.input.domain.model.UrlValidationException
import shortener.feature.links.input.presentation.generated.resources.Res
import shortener.feature.links.input.presentation.generated.resources.empty_url_error
import shortener.feature.links.input.presentation.generated.resources.invalid_url_error
import shortener.feature.links.input.presentation.generated.resources.link_already_added
import shortener.feature.links.input.presentation.generated.resources.shorten_url_error
import kotlin.test.Test
import kotlin.test.assertEquals

class ValidationErrorMessageFactoryImplTest {

    private val factory = ValidationErrorMessageFactoryImpl()

    @Test
    fun `given UrlValidationException Empty when create then returns empty_url_error`() {
        // Given
        val exception = UrlValidationException.Empty()

        // When
        val result = factory.create(exception)

        // Then
        assertEquals(Res.string.empty_url_error, result)
    }

    @Test
    fun `given UrlValidationException Invalid when create then returns invalid_url_error`() {
        // Given
        val exception = UrlValidationException.Invalid()

        // When
        val result = factory.create(exception)

        // Then
        assertEquals(Res.string.invalid_url_error, result)
    }

    @Test
    fun `given UrlValidationException LinkAlreadyAdded when create then returns link_already_added`() {
        // Given
        val exception = UrlValidationException.LinkAlreadyAdded()

        // When
        val result = factory.create(exception)

        // Then
        assertEquals(Res.string.link_already_added, result)
    }

    @Test
    fun `given non-UrlValidationException when create then returns shorten_url_error`() {
        // Given
        val exception = RuntimeException("Network error")

        // When
        val result = factory.create(exception)

        // Then
        assertEquals(Res.string.shorten_url_error, result)
    }
}
