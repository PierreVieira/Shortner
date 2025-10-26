package com.pierre.shortner.ui.utils.string_provider

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.getString
import shortener.ui.utils.generated.resources.Res
import shortener.ui.utils.generated.resources.test
import kotlin.test.Test
import kotlin.test.assertEquals

@InternalResourceApi
@OptIn(ExperimentalCoroutinesApi::class)
class StringProviderImplTest {

    private val mockedStringResource = Res.string.test

    @Test
    fun `Given a StringResource When getString is called Then it should return the correct string`() =
        runTest(StandardTestDispatcher()) {
            // Given
            val stringProvider = StringProviderImpl()
            val expected = getString(mockedStringResource)

            // When
            val result = stringProvider.getString(mockedStringResource)

            // Then
            assertEquals(expected, result)
        }
}
