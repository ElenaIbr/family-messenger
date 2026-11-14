package com.elenaibraeva.familymessenger.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import com.elenaibraeva.familymessenger.core.designsystem.theme.FamilyTheme

@Composable
fun BrandBackground(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    val colors = FamilyTheme.colors

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        colors.brandGradientStart,
                        colors.brandGradientEnd
                    )
                )
            )
            .safeDrawingPadding()
    ) {
        content()
    }
}
