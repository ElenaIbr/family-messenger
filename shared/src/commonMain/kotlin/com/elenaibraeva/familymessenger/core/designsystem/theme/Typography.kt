package com.elenaibraeva.familymessenger.core.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.elenaibraeva.familymessenger.generated.resources.Res
import com.elenaibraeva.familymessenger.generated.resources.inter_bold
import com.elenaibraeva.familymessenger.generated.resources.inter_medium
import com.elenaibraeva.familymessenger.generated.resources.inter_regular
import com.elenaibraeva.familymessenger.generated.resources.inter_semibold
import org.jetbrains.compose.resources.Font

@Composable
internal fun familyTypography(): Typography {
    val inter =
        FontFamily(
            Font(Res.font.inter_regular, FontWeight.Normal),
            Font(Res.font.inter_medium, FontWeight.Medium),
            Font(Res.font.inter_semibold, FontWeight.SemiBold),
            Font(Res.font.inter_bold, FontWeight.Bold)
        )

    val default = Typography()

    return Typography(
        displayLarge = default.displayLarge.copy(fontFamily = inter),
        displayMedium = default.displayMedium.copy(fontFamily = inter),
        displaySmall = default.displaySmall.copy(fontFamily = inter),
        headlineLarge =
        TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight.SemiBold,
            fontSize = 28.sp,
            lineHeight = 36.sp
        ),
        headlineMedium =
        TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            lineHeight = 32.sp
        ),
        headlineSmall = default.headlineSmall.copy(fontFamily = inter),
        titleLarge =
        TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            lineHeight = 28.sp
        ),
        titleMedium =
        TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            lineHeight = 24.sp
        ),
        titleSmall = default.titleSmall.copy(fontFamily = inter),
        bodyLarge =
        TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp
        ),
        bodyMedium =
        TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 20.sp
        ),
        bodySmall = default.bodySmall.copy(fontFamily = inter),
        labelLarge =
        TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp
        ),
        labelMedium = default.labelMedium.copy(fontFamily = inter),
        labelSmall = default.labelSmall.copy(fontFamily = inter)
    )
}
