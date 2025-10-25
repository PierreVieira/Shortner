package com.pierre.shortner.feature.links.input.presentation.factory

import org.jetbrains.compose.resources.StringResource

fun interface ValidationErrorMessageFactory {
    fun create(failure: Throwable): StringResource
}
