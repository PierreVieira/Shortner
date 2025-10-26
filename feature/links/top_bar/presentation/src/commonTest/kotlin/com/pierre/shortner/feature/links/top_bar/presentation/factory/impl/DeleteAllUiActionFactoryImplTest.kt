package com.pierre.shortner.feature.links.top_bar.presentation.factory.impl

import com.pierre.shortner.feature.links.top_bar.domain.usecase.ShouldShowDeleteAllDialog
import com.pierre.shortner.feature.links.top_bar.presentation.model.ShortenerTopBarUiAction
import com.pierre.shortner.model.routes.links.delete.DeleteAllLinksRoute
import kotlinx.coroutines.test.runTest
import shortener.feature.links.top_bar.presentation.generated.resources.Res
import shortener.feature.links.top_bar.presentation.generated.resources.there_are_no_links_to_delete
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DeleteAllUiActionFactoryImplTest {

    @Test
    fun `given shouldShowDeleteAllDialog returns true when create then returns NavigateToRoute`() = runTest {
        // Given
        val shouldShowDeleteAllDialog = MockShouldShowDeleteAllDialog()
        shouldShowDeleteAllDialog.invokeResult = true
        val factory = DeleteAllUiActionFactoryImpl(shouldShowDeleteAllDialog)

        // When
        val result = factory.create()

        // Then
        assertTrue(result is ShortenerTopBarUiAction.NavigateToRoute)
        assertEquals(DeleteAllLinksRoute, result.route)
    }

    @Test
    fun `given shouldShowDeleteAllDialog returns false when create then returns ShowSnackbar`() = runTest {
        // Given
        val shouldShowDeleteAllDialog = MockShouldShowDeleteAllDialog()
        shouldShowDeleteAllDialog.invokeResult = false
        val factory = DeleteAllUiActionFactoryImpl(shouldShowDeleteAllDialog)

        // When
        val result = factory.create()

        // Then
        assertTrue(result is ShortenerTopBarUiAction.ShowSnackbar)
        assertEquals(Res.string.there_are_no_links_to_delete, result.stringResource)
    }

    private class MockShouldShowDeleteAllDialog : ShouldShowDeleteAllDialog {
        var invokeResult: Boolean = false

        override suspend fun invoke(): Boolean = invokeResult
    }
}
