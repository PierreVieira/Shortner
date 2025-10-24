package com.pierre.shortner.feature.links.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.pierre.shortner.feature.links.domain.model.Link
import com.pierre.shortner.feature.links.presentation.model.event.LinksUiEvent
import com.pierre.shortner.ui.components.spacer.HorizontalSpacer
import com.pierre.shortner.ui.components.spacer.VerticalSpacer
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import shortener.feature.links.presentation.generated.resources.Res
import shortener.feature.links.presentation.generated.resources.alias
import shortener.feature.links.presentation.generated.resources.copy
import shortener.feature.links.presentation.generated.resources.more_options
import shortener.feature.links.presentation.generated.resources.original_link_label
import shortener.feature.links.presentation.generated.resources.remove
import shortener.feature.links.presentation.generated.resources.shortened_link_label

@Composable
fun LinkCard(
    link: Link,
    isMenuExpanded: Boolean,
    onEvent: (LinksUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
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
                    Text(
                        text = link.originalUrl,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                    VerticalSpacer(8)
                    Text(
                        text = stringResource(Res.string.shortened_link_label),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = link.shortenedUrl,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    VerticalSpacer(8)
                    Text(
                        text = stringResource(Res.string.alias),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = link.alias,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                HorizontalSpacer(8)
                
                // Overflow menu
                Box {
                    IconButton(
                        onClick = { onEvent(LinksUiEvent.OnMenuClick(link.id)) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(Res.string.more_options)
                        )
                    }
                    
                    DropdownMenu(
                        expanded = isMenuExpanded,
                        onDismissRequest = { onEvent(LinksUiEvent.OnMenuDismiss) }
                    ) {
                        DropdownMenuItem(
                            text = { Text(stringResource(Res.string.copy)) },
                            onClick = {
                                onEvent(LinksUiEvent.OnCopyLink(link.id))
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(stringResource(Res.string.remove)) },
                            onClick = {
                                onEvent(LinksUiEvent.OnDeleteLink(link.id))
                            }
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
        link = Link(
            id = 1,
            originalUrl = "https://www.example.com/very/long/url/that/might/overflow",
            shortenedUrl = "https://short.ly/abc123",
            alias = "abc123"
        ),
        isMenuExpanded = false,
        onEvent = {}
    )
}
