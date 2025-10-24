package com.pierre.shortner.feature.links.domain.model

sealed class UrlValidationException: IllegalArgumentException() {
    class Empty : UrlValidationException()
    class Invalid : UrlValidationException()
}
