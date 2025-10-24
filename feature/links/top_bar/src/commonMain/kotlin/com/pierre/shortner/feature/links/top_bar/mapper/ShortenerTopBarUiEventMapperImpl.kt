package com.pierre.shortner.feature.links.top_bar.mapper

import com.pierre.shortner.feature.links.top_bar.model.ShortenerTopBarUiEvent
import com.pierre.shortner.model.routes.DeleteAllRoute
import com.pierre.shortner.model.routes.theme.ThemeSettingsRoute

class ShortenerTopBarUiEventMapperImpl : ShortenerTopBarUiEventMapper {
    override fun map(event: ShortenerTopBarUiEvent): Any = when (event) {
        ShortenerTopBarUiEvent.ON_DELETE_ALL_CLICK -> DeleteAllRoute
        ShortenerTopBarUiEvent.ON_THEME_CHANGE_CLICK -> ThemeSettingsRoute
    }
}
