package com.pierre.shortner.feature.links.top_bar.presentation.mapper

import com.pierre.shortner.feature.links.top_bar.presentation.factory.DeleteAllUiActionFactory
import com.pierre.shortner.feature.links.top_bar.presentation.model.ShortenerTopBarUiAction
import com.pierre.shortner.feature.links.top_bar.presentation.model.ShortenerTopBarUiEvent
import com.pierre.shortner.model.routes.theme.ThemeSettingsRoute

class ShortenerTopBarUiEventToUiActionMapperImpl(
    private val deleteAllUiActionFactory: DeleteAllUiActionFactory,
): ShortenerTopBarUiEventToUiActionMapper {
    override suspend fun map(event: ShortenerTopBarUiEvent): ShortenerTopBarUiAction = when (event) {
        ShortenerTopBarUiEvent.ON_DELETE_ALL_CLICK -> deleteAllUiActionFactory.create()
        ShortenerTopBarUiEvent.ON_THEME_CHANGE_CLICK ->
            ShortenerTopBarUiAction.NavigateToRoute(ThemeSettingsRoute)
    }
}
