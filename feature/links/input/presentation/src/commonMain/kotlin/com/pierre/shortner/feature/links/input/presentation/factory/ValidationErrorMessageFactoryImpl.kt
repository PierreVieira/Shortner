package com.pierre.shortner.feature.links.input.presentation.factory

import com.pierre.shortner.feature.links.input.domain.model.UrlValidationException
import org.jetbrains.compose.resources.StringResource
import shortener.feature.links.input.presentation.generated.resources.Res
import shortener.feature.links.input.presentation.generated.resources.empty_url_error
import shortener.feature.links.input.presentation.generated.resources.invalid_url_error
import shortener.feature.links.input.presentation.generated.resources.link_already_added
import shortener.feature.links.input.presentation.generated.resources.shorten_url_error

class ValidationErrorMessageFactoryImpl : ValidationErrorMessageFactory {
    override fun create(failure: Throwable): StringResource =
        if (failure is UrlValidationException) {
            when (failure) {
                is UrlValidationException.Empty -> Res.string.empty_url_error
                is UrlValidationException.Invalid -> Res.string.invalid_url_error
                is UrlValidationException.LinkAlreadyAdded -> Res.string.link_already_added
            }
        } else {
            Res.string.shorten_url_error
        }
}
