package com.elenaibraeva.familymessenger.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class ExtendedColors(
    val online: Color,
    val success: Color,
    val warning: Color,

    // Messaging
    val incomingMessageBackground: Color,
    val outgoingMessageBackground: Color,
    val incomingMessageText: Color,
    val outgoingMessageText: Color,
    val messageTimestamp: Color,

    // Brand
    val brandTitle: Color,
    val brandGradientStart: Color,
    val brandGradientEnd: Color,
    val brandLogoContainer: Color,
    val brandOrbit: Color,
    val brandAvatarContainer: Color,
    val brandAvatarContent: Color
)

internal val LightExtendedColors =
    ExtendedColors(
        online = Green40,
        success = Green40,
        warning = Amber40,

        incomingMessageBackground = Violet90,
        outgoingMessageBackground = Violet40,
        incomingMessageText = Neutral20,
        outgoingMessageText = Neutral100,
        messageTimestamp = Neutral60,

        brandTitle = Violet20,
        brandGradientStart = Violet90,
        brandGradientEnd = Violet95,
        brandLogoContainer = Violet40,
        brandOrbit = Violet70,
        brandAvatarContainer = Neutral100,
        brandAvatarContent = Violet40
    )

internal val DarkExtendedColors =
    ExtendedColors(
        online = Green80,
        success = Green80,
        warning = Amber80,

        incomingMessageBackground = Neutral30,
        outgoingMessageBackground = Violet40,
        incomingMessageText = Neutral95,
        outgoingMessageText = Neutral100,
        messageTimestamp = Neutral70,

        brandTitle = Violet85,
        brandGradientStart = Neutral15,
        brandGradientEnd = Neutral10,
        brandLogoContainer = Violet40,
        brandOrbit = Neutral40,
        brandAvatarContainer = Violet30,
        brandAvatarContent = Violet85
    )
