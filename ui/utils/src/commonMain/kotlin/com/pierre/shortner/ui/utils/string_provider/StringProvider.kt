package com.pierre.shortner.ui.utils.string_provider

import org.jetbrains.compose.resources.StringResource

fun interface StringProvider {
    suspend fun getString(stringResource: StringResource): String
}
