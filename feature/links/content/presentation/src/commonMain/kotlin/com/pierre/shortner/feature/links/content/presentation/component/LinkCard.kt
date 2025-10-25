package com.pierre.shortner.feature.links.content.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import com.pierre.shortner.feature.links.content.presentation.model.LinkPresentationModel
import com.pierre.shortner.feature.links.content.presentation.model.event.LinksUiEvent
import com.pierre.shortner.ui.components.spacer.HorizontalSpacer
import com.pierre.shortner.ui.components.spacer.VerticalSpacer
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import shortener.feature.links.content.presentation.generated.resources.Res
import shortener.feature.links.content.presentation.generated.resources.alias
import shortener.feature.links.content.presentation.generated.resources.copy
import shortener.feature.links.content.presentation.generated.resources.more_options
import shortener.feature.links.content.presentation.generated.resources.original_link_label
import shortener.feature.links.content.presentation.generated.resources.remove
import shortener.feature.links.content.presentation.generated.resources.shortened_link_label

@Composable
private fun ClickableLinkText(
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
            .clickable { onEvent(onClickEvent) }
            .pointerInput(linkId) {
                detectTapGestures(
                    onLongPress = { onEvent(onLongPressEvent) }
                )
            }
    )
}

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
            // Original link with icons in the same line
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(Res.string.original_link_label),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    ClickableLinkText(
                        text = linkPresentationModel.originalUrl,
                        linkId = linkPresentationModel.id,
                        onEvent = onEvent,
                        onClickEvent = LinksUiEvent.OnOriginalLinkClick(linkPresentationModel.id),
                        onLongPressEvent = LinksUiEvent.OnOriginalLinkLongPress(linkPresentationModel.id)
                    )
                }

                HorizontalSpacer(8)

                // Icons row
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Collapse/Expand arrow indicator with rotation animation
                    val rotationAngle by animateFloatAsState(
                        targetValue = if (linkPresentationModel.isCardExpanded) 180f else 0f,
                        animationSpec = tween(300),
                        label = "arrow_rotation"
                    )

                    IconButton(
                        onClick = { onEvent(LinksUiEvent.OnToggleCardCollapse(linkPresentationModel.id)) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ExpandMore,
                            contentDescription = if (linkPresentationModel.isCardExpanded) "Collapse" else "Expand",
                            modifier = Modifier.rotate(rotationAngle)
                        )
                    }

                    // Overflow menu
                    Box {
                        IconButton(
                            onClick = { onEvent(LinksUiEvent.OnMenuClick(linkPresentationModel.id)) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = stringResource(Res.string.more_options)
                            )
                        }

                        DropdownMenu(
                            expanded = linkPresentationModel.isMenuExpanded,
                            onDismissRequest = { onEvent(LinksUiEvent.OnMenuDismiss) }
                        ) {
                            DropdownMenuItem(
                                text = { Text(stringResource(Res.string.copy)) },
                                onClick = {
                                    onEvent(LinksUiEvent.OnCopyLink(linkPresentationModel.id))
                                }
                            )
                            DropdownMenuItem(
                                text = { Text(stringResource(Res.string.remove)) },
                                onClick = {
                                    onEvent(LinksUiEvent.OnDeleteLink(linkPresentationModel.id))
                                }
                            )
                        }
                    }
                }
            }

            // Animated visibility for additional information (full width)
            AnimatedVisibility(
                visible = linkPresentationModel.isCardExpanded,
                enter = expandVertically(animationSpec = tween(300)),
                exit = shrinkVertically(animationSpec = tween(300))
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    VerticalSpacer(8)
                    Text(
                        text = stringResource(Res.string.shortened_link_label),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
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
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = linkPresentationModel.alias,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    VerticalSpacer(8)

                    // Date text positioned at bottom right
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = linkPresentationModel.createdAt,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontStyle = FontStyle.Italic
                        )
                    }
                }
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
