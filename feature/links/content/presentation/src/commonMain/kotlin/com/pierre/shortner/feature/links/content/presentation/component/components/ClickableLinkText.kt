package com.pierre.shortner.feature.links.content.presentation.component.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import com.pierre.shortner.feature.links.content.presentation.model.event.LinksUiEvent

@Composable
internal fun ClickableLinkText(
    text: String,
    linkId: Long,
    onEvent: (LinksUiEvent) -> Unit,
    onClickEvent: LinksUiEvent,
    onLongPressEvent: LinksUiEvent,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.primary,
        textDecoration = TextDecoration.Underline,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
            .pointerInput(linkId) {
                detectTapGestures(
                    onTap = { onEvent(onClickEvent) },
                    onLongPress = { onEvent(onLongPressEvent) }
                )
            }
    )
}
