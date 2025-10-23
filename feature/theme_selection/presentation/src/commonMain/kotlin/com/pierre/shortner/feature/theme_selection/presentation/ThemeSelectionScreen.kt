package com.pierre.shortner.feature.theme_selection.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pierre.shortner.feature.theme_selection.presentation.component.ThemeOptionCard
import com.pierre.shortner.feature.theme_selection.presentation.factory.ThemeOptionsFactory
import com.pierre.shortner.feature.theme_selection.presentation.model.ThemeSelectionModel
import com.pierre.shortner.feature.theme_selection.presentation.model.ThemeSelectionUiEvent
import com.pierre.shortner.model.theme.Theme
import com.pierre.shortner.ui.components.icon_button.ArrowBackIconButton
import com.pierre.shortner.ui.theme.preview.AllThemePreferencesPreviewParameterProvider
import com.pierre.shortner.ui.theme.preview.PreviewTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeSelectionScreen(
    switchPlatformColorSchemeComponent: @Composable (Modifier) -> Unit,
    options: List<ThemeSelectionModel>,
    onEvent: (ThemeSelectionUiEvent) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        ArrowBackIconButton(onClick = { onEvent(ThemeSelectionUiEvent.OnBackClick) })
                        switchPlatformColorSchemeComponent(Modifier)
                    }
                }
            )
        },
        contentWindowInsets = WindowInsets.safeDrawing,
    ) { paddingValues: PaddingValues ->
        BoxWithConstraints(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
        ) {
            val isWide = maxWidth > 600.dp
            val commonModifier = Modifier.align(Alignment.Center)
            val onSetTheme = { theme: Theme ->
                onEvent(ThemeSelectionUiEvent.OnThemeSelected(theme))
            }
            if (isWide) {
                Row(
                    modifier = commonModifier,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    options.forEach {
                        ThemeOptionCard(
                            model = it,
                            onClick = onSetTheme,
                            modifier = Modifier.weight(1f),
                        )
                    }
                }
            } else {
                LazyColumn(modifier = commonModifier, verticalArrangement = Arrangement.spacedBy(space = 16.dp)) {
                    items(options) {
                        ThemeOptionCard(
                            model = it,
                            onClick = onSetTheme,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ThemeSelectionScreenPreview(
    @PreviewParameter(AllThemePreferencesPreviewParameterProvider::class)
    currentTheme: Theme,
) {
    PreviewTheme(currentTheme) {
        ThemeSelectionScreen(
            options = ThemeOptionsFactory.themeOptions,
            onEvent = {},
            switchPlatformColorSchemeComponent = {
                Switch(checked = true, onCheckedChange = {})
            }
        )
    }
}
