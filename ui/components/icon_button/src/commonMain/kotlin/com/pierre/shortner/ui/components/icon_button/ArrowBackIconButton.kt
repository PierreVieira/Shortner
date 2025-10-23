package com.pierre.shortner.ui.components.icon_button

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import shortener.ui.components.icon_button.generated.resources.Res
import shortener.ui.components.icon_button.generated.resources.back

@Composable
fun ArrowBackIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    CommonIconButton(
        modifier = modifier,
        onClick = onClick,
        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
        contentDescription = stringResource(Res.string.back)
    )
}
