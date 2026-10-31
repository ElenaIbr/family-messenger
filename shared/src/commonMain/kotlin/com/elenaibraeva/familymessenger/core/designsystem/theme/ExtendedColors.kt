package com.elenaibraeva.familymessenger.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class ExtendedColors(
    val online: Color,
    val success: Color,
    val warning: Color,
    val incomingMessageBackground: Color,
    val outgoingMessageBackground: Color,
    val incomingMessageText: Color,
    val outgoingMessageText: Color,
    val messageTimestamp: Color
)

internal val LightExtendedColors =
    ExtendedColors(
        online = Green40,
        success = Green40,
        warning = Amber40,
        incomingMessageBackground = Neutral94,
        outgoingMessageBackground = Violet90,
        incomingMessageText = Neutral20,
        outgoingMessageText = Neutral20,
        messageTimestamp = Neutral60
    )

internal val DarkExtendedColors =
    ExtendedColors(
        online = Green80,
        success = Green80,
        warning = Amber80,
        incomingMessageBackground = Neutral30,
        outgoingMessageBackground = Violet30,
        incomingMessageText = Neutral95,
        outgoingMessageText = Neutral95,
        messageTimestamp = Neutral70
    )
