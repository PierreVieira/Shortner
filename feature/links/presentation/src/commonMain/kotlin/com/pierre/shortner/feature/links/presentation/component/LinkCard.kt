package com.pierre.shortner.feature.links.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.pierre.shortner.ui.components.icon_button.CommonIconButton
import com.pierre.shortner.ui.components.spacer.HorizontalSpacer
import com.pierre.shortner.ui.components.spacer.VerticalSpacer
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import shortener.feature.links.presentation.generated.resources.Res
import shortener.feature.links.presentation.generated.resources.alias
import shortener.feature.links.presentation.generated.resources.delete_link_button
import shortener.feature.links.presentation.generated.resources.original_link_label
import shortener.feature.links.presentation.generated.resources.shortened_link_label

@Composable
fun LinkCard(
    link: Link,
    onDeleteClick: () -> Unit,
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
                CommonIconButton(
                    onClick = onDeleteClick,
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(Res.string.delete_link_button),
                )
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
        onDeleteClick = {}
    )
}
