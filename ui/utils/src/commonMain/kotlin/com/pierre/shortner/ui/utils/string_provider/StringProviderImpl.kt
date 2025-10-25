package com.pierre.shortner.ui.utils.string_provider

import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString as composeGetString

class StringProviderImpl : StringProvider {
    override suspend fun getString(stringResource: StringResource): String =
        composeGetString(stringResource)
}
