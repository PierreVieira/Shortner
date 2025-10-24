package com.pierre.shortner.feature.links.presentation

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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.pierre.shortner.feature.links.input.presentation.UrlInputFieldRoot
import com.pierre.shortner.feature.links.presentation.component.LinkCard
import com.pierre.shortner.feature.links.presentation.model.action.LinksUiAction
import com.pierre.shortner.feature.links.presentation.model.event.LinksUiEvent
import com.pierre.shortner.feature.links.presentation.model.state.LinksUiState
import com.pierre.shortner.feature.links.presentation.screen.LinksScreen
import com.pierre.shortner.feature.links.presentation.viewmodel.LinksViewModel
import com.pierre.shortner.feature.links.top_bar.presentation.ShortenerTopAppBarRoot
import com.pierre.shortner.model.routes.LinksRoute
import com.pierre.shortner.ui.utils.ActionCollector
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import shortener.feature.links.presentation.generated.resources.Res
import shortener.feature.links.presentation.generated.resources.no_links_message

fun NavGraphBuilder.links(
    navController: NavController,
) {
    composable<LinksRoute> {
        val viewModel = koinViewModel<LinksViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }

        ActionCollector(viewModel.uiActions) { action ->
            when (action) {
                is LinksUiAction.ShowSnackbar -> snackbarHostState
                    .showSnackbar(getString(action.resourceId))

                is LinksUiAction.Navigate -> navController.navigate(action.route)
            }
        }

        LinksScreen(
            snackbarHostState = snackbarHostState,
            topBar = { ShortenerTopAppBarRoot(navController, snackbarHostState) },
            inputField = { UrlInputFieldRoot(snackbarHostState) },
            linksContent = {
                LinksContentRoot(uiState, viewModel::onEvent)
            }
        )
    }
}

@Composable
private fun LinksContentRoot(
    uiState: LinksUiState,
    onEvent: (LinksUiEvent) -> Unit,
) {
    AnimatedContent(
        targetState = uiState.links.isEmpty(),
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
                    items = uiState.links,
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

