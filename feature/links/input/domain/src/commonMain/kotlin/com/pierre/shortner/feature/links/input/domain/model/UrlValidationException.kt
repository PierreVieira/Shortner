package com.pierre.shortner.feature.links.input.domain.model

sealed class UrlValidationException: IllegalArgumentException() {
    class Empty : UrlValidationException()
    class Invalid : UrlValidationException()
}
