package com.elenaibraeva.familymessenger.core.designsystem.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

internal val LightColorScheme: ColorScheme = lightColorScheme(
    primary = Violet40,
    onPrimary = Neutral100,
    primaryContainer = Violet90,
    onPrimaryContainer = Violet20,

    background = Neutral100,
    onBackground = Neutral20,

    surface = Neutral100,
    onSurface = Neutral20,

    surfaceVariant = Neutral95,
    onSurfaceVariant = Neutral60,

    outline = Neutral90,

    error = Red40,
    onError = Neutral100,
    errorContainer = Red90,
    onErrorContainer = Red40,
)

internal val DarkColorScheme: ColorScheme = darkColorScheme(
    primary = Violet80,
    onPrimary = Violet20,
    primaryContainer = Violet30,
    onPrimaryContainer = Violet80,

    background = Neutral10,
    onBackground = Neutral95,

    surface = Neutral20,
    onSurface = Neutral95,

    surfaceVariant = Neutral30,
    onSurfaceVariant = Neutral70,

    outline = Neutral40,

    error = Red80,
    onError = Neutral10,
    errorContainer = Red40,
    onErrorContainer = Neutral100,
)
