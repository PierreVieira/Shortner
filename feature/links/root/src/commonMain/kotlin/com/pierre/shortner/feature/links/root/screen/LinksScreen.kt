package com.pierre.shortner.feature.links.root.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pierre.shortner.ui.components.spacer.VerticalSpacer
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LinksScreen(
    snackbarHostState: SnackbarHostState,
    topBar: @Composable () -> Unit,
    inputField: @Composable () -> Unit,
    linksContent: @Composable () -> Unit
) {
    Scaffold(
        topBar = topBar,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        contentWindowInsets = WindowInsets.safeDrawing,
    ) { paddingValues: PaddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            inputField()
            VerticalSpacer()
            linksContent()
        }
    }
}

@Preview
@Composable
private fun LinksScreenPreview() {
    LinksScreen(
        snackbarHostState = remember { SnackbarHostState() },
        topBar = {},
        inputField = {},
        linksContent = {},
    )
}
