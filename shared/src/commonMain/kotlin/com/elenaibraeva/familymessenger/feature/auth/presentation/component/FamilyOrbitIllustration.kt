package com.elenaibraeva.familymessenger.feature.auth.presentation.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elenaibraeva.familymessenger.core.designsystem.component.FMText
import com.elenaibraeva.familymessenger.core.designsystem.theme.FamilyMessengerTheme
import com.elenaibraeva.familymessenger.core.designsystem.theme.FamilyTheme
import com.elenaibraeva.familymessenger.generated.resources.Res
import com.elenaibraeva.familymessenger.generated.resources.ic_family_messenger_white
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import org.jetbrains.compose.resources.painterResource

private val contacts = listOf("M", "D", "S", "A")

@Composable
fun FamilyOrbitIllustration(modifier: Modifier = Modifier, animate: Boolean = true) {
    val colors = FamilyTheme.colors
    val rotation = remember { Animatable(0f) }

    LaunchedEffect(animate) {
        rotation.snapTo(0f)

        if (animate) {
            rotation.animateTo(
                targetValue = 360f,
                animationSpec = tween(
                    durationMillis = 2800,
                    easing = FastOutSlowInEasing
                )
            )
        }
    }

    BoxWithConstraints(
        modifier = modifier.aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        val orbitRadius = maxWidth * 0.34f
        val avatarSize = maxWidth * 0.19f
        val centerSize = maxWidth * 0.40f

        // Dashed orbit
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawCircle(
                color = colors.brandOrbit,
                radius = orbitRadius.toPx(),
                style = Stroke(
                    width = 2.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(
                        intervals = floatArrayOf(
                            6.dp.toPx(),
                            7.dp.toPx()
                        )
                    )
                )
            )
        }

        // Central logo
        Box(
            modifier = Modifier
                .size(centerSize)
                .background(
                    color = colors.brandLogoContainer,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(
                    Res.drawable.ic_family_messenger_white
                ),
                contentDescription = null,
                modifier = Modifier.size(centerSize * 0.85f)
            )
        }

        // Orbiting family members
        contacts.forEachIndexed { index, initials ->
            val angle = (
                -135f + index * 90f + rotation.value
                ) * PI / 180.0

            val x = orbitRadius * cos(angle).toFloat()
            val y = orbitRadius * sin(angle).toFloat()

            OrbitAvatar(
                initials = initials,
                modifier = Modifier
                    .offset(x = x, y = y)
                    .size(avatarSize)
            )
        }
    }
}

@Composable
private fun OrbitAvatar(initials: String, modifier: Modifier = Modifier) {
    val colors = FamilyTheme.colors

    Box(
        modifier = modifier.background(
            color = colors.brandAvatarContainer,
            shape = CircleShape
        ),
        contentAlignment = Alignment.Center
    ) {
        FMText(
            text = initials,
            color = colors.brandAvatarContent,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}

@Preview(name = "Family Orbit - Light")
@Composable
private fun FamilyOrbitLightPreview() {
    FamilyMessengerTheme(darkTheme = false) {
        FamilyOrbitIllustration(
            modifier = Modifier
                .size(280.dp)
                .background(FamilyTheme.colors.brandGradientStart),
            animate = false
        )
    }
}

@Preview(name = "Family Orbit - Dark")
@Composable
private fun FamilyOrbitDarkPreview() {
    FamilyMessengerTheme(darkTheme = true) {
        FamilyOrbitIllustration(
            modifier = Modifier
                .size(280.dp)
                .background(FamilyTheme.colors.brandGradientStart),
            animate = false
        )
    }
}
