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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elenaibraeva.familymessenger.core.designsystem.theme.FamilyMessengerTheme
import com.elenaibraeva.familymessenger.core.designsystem.theme.FamilyTheme
import com.elenaibraeva.familymessenger.generated.resources.Res
import com.elenaibraeva.familymessenger.generated.resources.avatar_cat
import com.elenaibraeva.familymessenger.generated.resources.avatar_man
import com.elenaibraeva.familymessenger.generated.resources.avatar_woman_1
import com.elenaibraeva.familymessenger.generated.resources.avatar_woman_2
import com.elenaibraeva.familymessenger.generated.resources.ic_family_messenger_together
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

private val avatars = listOf(
    Res.drawable.avatar_cat,
    Res.drawable.avatar_man,
    Res.drawable.avatar_woman_1,
    Res.drawable.avatar_woman_2
)

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
        val orbitRadius = maxWidth * 0.35f
        val avatarSize = maxWidth * 0.24f
        val centerSize = maxWidth * 0.34f

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
                    Res.drawable.ic_family_messenger_together
                ),
                contentDescription = null,
                modifier = Modifier.size(centerSize * 0.65f)
            )
        }

        // Orbiting family members
        avatars.forEachIndexed { index, avatar ->
            val angle = (
                -135f + index * 90f + rotation.value
                ) * PI / 180.0

            val x = orbitRadius * cos(angle).toFloat()
            val y = orbitRadius * sin(angle).toFloat()

            OrbitAvatar(
                avatar = avatar,
                modifier = Modifier
                    .offset(x = x, y = y)
                    .size(avatarSize)
            )
        }
    }
}

@Composable
private fun OrbitAvatar(avatar: DrawableResource, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(avatar),
        contentDescription = null,
        modifier = modifier
            .background(
                color = FamilyTheme.colors.brandGradientStart,
                shape = CircleShape
            )
            .clip(CircleShape),
        contentScale = ContentScale.Fit
    )
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
