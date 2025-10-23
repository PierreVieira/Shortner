package com.pierre.shortner.feature.theme_selection.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import com.pierre.shortner.model.theme.Theme
import com.pierre.shortner.ui.theme.utils.LocalThemeOption
import com.pierre.shortner.feature.theme_selection.presentation.model.ThemeSelectionModel
import com.pierre.shortner.ui.theme.preview.AllThemePreferencesPreviewParameterProvider
import com.pierre.shortner.ui.theme.preview.PreviewTheme
import shortener.feature.theme_selection.presentation.generated.resources.Res
import shortener.feature.theme_selection.presentation.generated.resources.light_description
import shortener.feature.theme_selection.presentation.generated.resources.light_title

@Composable
fun ThemeOptionCard(
    model: ThemeSelectionModel,
    onClick: (Theme) -> Unit,
    modifier: Modifier = Modifier,
) {
    val currentTheme = LocalThemeOption.current
    val isSelected = currentTheme == model.preference
    val primary = MaterialTheme.colorScheme.primary
    model.run {
        val borderColor = if (isSelected) primary else Color.Transparent
        val containerColor =
            if (isSelected) primary.copy(alpha = 0.06f) else MaterialTheme.colorScheme.surface

        Card(
            onClick = { onClick(preference) },
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = containerColor),
            border = BorderStroke(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) borderColor else MaterialTheme.colorScheme.outline.copy(
                    alpha = 0.2f
                ),
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            modifier = modifier,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                )
                Text(
                    text = stringResource(title),
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                )
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = stringResource(subtitle),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ThemeOptionCardPreview(
    @PreviewParameter(AllThemePreferencesPreviewParameterProvider::class)
    currentTheme: Theme,
) {
    PreviewTheme(currentTheme) {
        ThemeOptionCard(
            model = ThemeSelectionModel(
                title = Res.string.light_title,
                subtitle = Res.string.light_description,
                icon = Icons.Filled.LightMode,
                preference = Theme.LIGHT,
            ),
            onClick = {},
        )
    }
}
