package com.pierre.shortner.feature.links.content.presentation.component.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.pierre.shortner.feature.links.content.presentation.model.LinkPresentationModel
import com.pierre.shortner.feature.links.content.presentation.model.event.LinksUiEvent
import com.pierre.shortner.ui.components.spacer.VerticalSpacer
import org.jetbrains.compose.resources.stringResource
import shortener.feature.links.content.presentation.generated.resources.Res
import shortener.feature.links.content.presentation.generated.resources.alias
import shortener.feature.links.content.presentation.generated.resources.shortened_link_label

@Composable
internal fun LinkCardContent(
    linkPresentationModel: LinkPresentationModel,
    onEvent: (LinksUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(Res.string.shortened_link_label),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold
        )
        ClickableLinkText(
            text = linkPresentationModel.shortenedUrl,
            linkId = linkPresentationModel.id,
            onEvent = onEvent,
            onClickEvent = LinksUiEvent.OnShortenedLinkClick(linkPresentationModel.id),
            onLongPressEvent = LinksUiEvent.OnShortenedLinkLongPress(linkPresentationModel.id)
        )
        VerticalSpacer(8)
        Text(
            text = stringResource(Res.string.alias),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = linkPresentationModel.alias,
            style = MaterialTheme.typography.bodyMedium
        )
        VerticalSpacer(8)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = linkPresentationModel.createdAt,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
