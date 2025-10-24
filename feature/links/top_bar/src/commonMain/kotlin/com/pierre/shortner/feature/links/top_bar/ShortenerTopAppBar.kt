package com.pierre.shortner.feature.links.top_bar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import com.pierre.shortner.feature.links.top_bar.model.ShortenerTopBarButtonModel
import com.pierre.shortner.feature.links.top_bar.model.ShortenerTopBarUiEvent
import com.pierre.shortner.ui.components.icon_button.CommonIconButton
import org.jetbrains.compose.resources.stringResource
import shortener.feature.links.top_bar.generated.resources.Res
import shortener.feature.links.top_bar.generated.resources.shortener_screen_title

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShortenerTopAppBar(
    buttons: List<ShortenerTopBarButtonModel>,
    onEvent: (ShortenerTopBarUiEvent) -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(Res.string.shortener_screen_title),
            )
        },
        actions = {
            buttons.forEach { buttonModel ->
                buttonModel.run {
                    CommonIconButton(
                        imageVector = imageVector,
                        contentDescription = stringResource(contentDescriptionResource),
                        onClick = { onEvent(uiEvent) }
                    )
                }
            }
        }
    )
}
