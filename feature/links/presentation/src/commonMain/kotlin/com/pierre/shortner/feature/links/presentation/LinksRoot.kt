package com.pierre.shortner.feature.links.presentation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.pierre.shortner.feature.links.content.presentation.LinksContentRoot
import com.pierre.shortner.feature.links.input.presentation.UrlInputFieldRoot
import com.pierre.shortner.feature.links.presentation.screen.LinksScreen
import com.pierre.shortner.feature.links.top_bar.presentation.ShortenerTopAppBarRoot
import com.pierre.shortner.model.routes.LinksRoute

fun NavGraphBuilder.links(
    navController: NavController,
) {
    composable<LinksRoute> {
        val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
        LinksScreen(
            snackbarHostState = snackbarHostState,
            topBar = { ShortenerTopAppBarRoot(navController, snackbarHostState) },
            inputField = { UrlInputFieldRoot(snackbarHostState) },
            linksContent = { LinksContentRoot(navController, snackbarHostState) }
        )
    }
}
