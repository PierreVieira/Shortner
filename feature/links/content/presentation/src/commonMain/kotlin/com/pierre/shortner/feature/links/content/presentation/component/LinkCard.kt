package com.pierre.shortner.feature.links.content.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import com.pierre.shortner.feature.links.content.presentation.model.LinkPresentationModel
import com.pierre.shortner.feature.links.content.presentation.model.event.LinksUiEvent
import com.pierre.shortner.ui.components.icon_button.CommonIconButton
import com.pierre.shortner.ui.components.spacer.HorizontalSpacer
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import shortener.feature.links.content.presentation.generated.resources.Res
import shortener.feature.links.content.presentation.generated.resources.collapse
import shortener.feature.links.content.presentation.generated.resources.expand
import shortener.feature.links.content.presentation.generated.resources.remove

@Composable
fun LinkCard(
    linkPresentationModel: LinkPresentationModel,
    onEvent: (LinksUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = { onEvent(LinksUiEvent.OnToggleCardCollapse(linkPresentationModel.id)) },
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ClickableLinkText(
                    modifier = Modifier.weight(1f),
                    text = linkPresentationModel.originalUrl,
                    linkId = linkPresentationModel.id,
                    onEvent = onEvent,
                    onClickEvent = LinksUiEvent.OnOriginalLinkClick(linkPresentationModel.id),
                    onLongPressEvent = LinksUiEvent.OnOriginalLinkLongPress(
                        linkPresentationModel.id
                    )
                )
                HorizontalSpacer(8)
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val rotationAngle by animateFloatAsState(
                        targetValue = if (linkPresentationModel.isCardExpanded) 180f else 0f,
                        animationSpec = tween(300),
                        label = "arrow_rotation"
                    )
                    CommonIconButton(
                        modifier = Modifier.rotate(rotationAngle),
                        imageVector = Icons.Default.ExpandMore,
                        contentDescription = stringResource(
                            if (linkPresentationModel.isCardExpanded) {
                                Res.string.collapse
                            } else {
                                Res.string.expand
                            }
                        ),
                        onClick = { onEvent(LinksUiEvent.OnToggleCardCollapse(linkPresentationModel.id)) }
                    )
                    CommonIconButton(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(Res.string.remove),
                        onClick = { onEvent(LinksUiEvent.OnDeleteLink(linkPresentationModel.id)) }
                    )
                }
            }
            AnimatedVisibility(
                visible = linkPresentationModel.isCardExpanded,
                enter = expandVertically(animationSpec = tween(300)),
                exit = shrinkVertically(animationSpec = tween(300))
            ) {
                LinkCardContent(
                    linkPresentationModel = linkPresentationModel,
                    onEvent = onEvent
                )
            }
        }
    }
}

@Preview
@Composable
private fun LinkCardPreview() {
    LinkCard(
        linkPresentationModel = LinkPresentationModel(
            id = 1,
            originalUrl = "https://www.example.com/very/long/url/that/might/overflow",
            shortenedUrl = "https://short.ly/abc123",
            alias = "abc123",
            createdAt = "15/01/2024 às 10:30:45",
            isCardExpanded = false,
            isMenuExpanded = false
        ),
        onEvent = {}
    )
}
