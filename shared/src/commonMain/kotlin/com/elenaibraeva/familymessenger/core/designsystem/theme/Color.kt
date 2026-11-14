package com.elenaibraeva.familymessenger.core.designsystem.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

internal val LightColorScheme: ColorScheme =
    lightColorScheme(
        primary = Violet40,
        onPrimary = Neutral100,
        primaryContainer = Violet90,
        onPrimaryContainer = Violet20,

        secondary = Neutral60,
        onSecondary = Neutral100,
        secondaryContainer = Neutral94,
        onSecondaryContainer = Neutral20,

        tertiary = Violet20,
        onTertiary = Neutral100,
        tertiaryContainer = Violet85,
        onTertiaryContainer = Violet10,

        background = Violet95,
        onBackground = Neutral20,

        surface = Neutral100,
        onSurface = Neutral20,
        surfaceVariant = Neutral94,
        onSurfaceVariant = Neutral60,

        surfaceContainerLowest = Neutral100,
        surfaceContainerLow = Violet95,
        surfaceContainer = Neutral94,
        surfaceContainerHigh = Violet90,
        surfaceContainerHighest = Violet85,

        outline = Neutral60,
        outlineVariant = Violet70,

        error = Red40,
        onError = Neutral100,
        errorContainer = Red90,
        onErrorContainer = Red40
    )

internal val DarkColorScheme: ColorScheme =
    darkColorScheme(
        primary = Violet80,
        onPrimary = Violet10,
        primaryContainer = Violet30,
        onPrimaryContainer = Violet85,

        secondary = Neutral70,
        onSecondary = Neutral20,
        secondaryContainer = Neutral30,
        onSecondaryContainer = Neutral95,

        tertiary = Violet85,
        onTertiary = Violet10,
        tertiaryContainer = Violet30,
        onTertiaryContainer = Violet85,

        background = Neutral10,
        onBackground = Neutral95,

        surface = Neutral30,
        onSurface = Neutral95,
        surfaceVariant = Violet30,
        onSurfaceVariant = Neutral70,

        surfaceContainerLowest = Neutral10,
        surfaceContainerLow = Neutral15,
        surfaceContainer = Neutral20,
        surfaceContainerHigh = Neutral30,
        surfaceContainerHighest = Violet30,

        outline = Neutral70,
        outlineVariant = Neutral40,

        error = Red80,
        onError = Neutral10,
        errorContainer = Red40,
        onErrorContainer = Neutral100
    )
