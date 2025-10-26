package com.pierre.shortner.feature.links.top_bar.presentation.mapper

import com.pierre.shortner.feature.links.top_bar.presentation.factory.DeleteAllUiActionFactory
import com.pierre.shortner.feature.links.top_bar.presentation.model.ShortenerTopBarUiAction
import com.pierre.shortner.feature.links.top_bar.presentation.model.ShortenerTopBarUiEvent
import com.pierre.shortner.model.routes.links.delete.DeleteAllLinksRoute
import com.pierre.shortner.model.routes.theme.ThemeSettingsRoute
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ShortenerTopBarUiEventToUiActionMapperImplTest {

    @Test
    fun `given ON_DELETE_ALL_CLICK when map then returns result from deleteAllUiActionFactory`() = runTest {
        // Given
        val deleteAllUiActionFactory = MockDeleteAllUiActionFactory()
        val expectedAction = ShortenerTopBarUiAction.NavigateToRoute(DeleteAllLinksRoute)
        deleteAllUiActionFactory.createResult = expectedAction
        val mapper = ShortenerTopBarUiEventToUiActionMapperImpl(deleteAllUiActionFactory)

        // When
        val result = mapper.map(ShortenerTopBarUiEvent.ON_DELETE_ALL_CLICK)

        // Then
        assertEquals(expectedAction, result)
    }

    @Test
    fun `given ON_THEME_CHANGE_CLICK when map then returns NavigateToRoute with ThemeSettingsRoute`() = runTest {
        // Given
        val deleteAllUiActionFactory = MockDeleteAllUiActionFactory()
        val mapper = ShortenerTopBarUiEventToUiActionMapperImpl(deleteAllUiActionFactory)

        // When
        val result = mapper.map(ShortenerTopBarUiEvent.ON_THEME_CHANGE_CLICK)

        // Then
        assertTrue(result is ShortenerTopBarUiAction.NavigateToRoute)
        assertEquals(ThemeSettingsRoute, result.route)
    }

    private class MockDeleteAllUiActionFactory : DeleteAllUiActionFactory {
        var createResult: ShortenerTopBarUiAction = ShortenerTopBarUiAction.NavigateToRoute(DeleteAllLinksRoute)

        override suspend fun create(): ShortenerTopBarUiAction = createResult
    }
}
