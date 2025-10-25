package com.pierre.shortner.feature.links.content.presentation.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pierre.shortner.feature.links.content.presentation.model.LinkPresentationModel
import com.pierre.shortner.feature.links.content.presentation.model.event.LinksUiEvent
import org.jetbrains.compose.resources.stringResource
import shortener.feature.links.content.presentation.generated.resources.Res
import shortener.feature.links.content.presentation.generated.resources.no_links_message

@Composable
fun LoadedLinksContent(
    links: List<LinkPresentationModel>,
    onEvent: (LinksUiEvent) -> Unit,
) {
    AnimatedContent(
        targetState = links.isEmpty(),
        transitionSpec = {
            fadeIn(animationSpec = tween(400)) togetherWith fadeOut(animationSpec = tween(400))
        },
        label = "empty_list_transition"
    ) { isEmpty ->
        if (isEmpty) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(Res.string.no_links_message),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 80.dp) // Space for FAB
            ) {
                items(
                    items = links,
                    key = { it.id },
                ) { linkPresentationModel ->
                    LinkCard(
                        modifier = Modifier.fillMaxWidth().animateItem(),
                        linkPresentationModel = linkPresentationModel,
                        onEvent = onEvent,
                    )
                }
            }
        }
    }
}
