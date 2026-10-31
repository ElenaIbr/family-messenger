package com.elenaibraeva.familymessenger.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import com.elenaibraeva.familymessenger.core.designsystem.theme.FamilyMessengerTheme
import com.elenaibraeva.familymessenger.generated.resources.Res
import com.elenaibraeva.familymessenger.generated.resources.ic_person
import org.jetbrains.compose.resources.painterResource

@Composable
fun FMAvatar(
    imageUrl: String?,
    initials: String?,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    ) {
        if (imageUrl.isNullOrBlank()) {
            FMAvatarPlaceholder(initials)
        } else {
            SubcomposeAsyncImage(
                model = imageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                loading = {
                    FMAvatarPlaceholder(initials)
                },
                error = {
                    FMAvatarPlaceholder(initials)
                },
                success = {
                    SubcomposeAsyncImageContent()
                }
            )
        }
    }
}

@Composable
private fun FMAvatarPlaceholder(initials: String?) {
    if (!initials.isNullOrBlank()) {
        FMText(
            text = initials.take(2).uppercase(),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    } else {
        Icon(
            painter = painterResource(Res.drawable.ic_person),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Preview(name = "Avatar - Light")
@Composable
private fun FMAvatarLightPreview() {
    FamilyMessengerTheme(darkTheme = false) {
        FMAvatarPreviewContent()
    }
}

@Preview(name = "Avatar - Dark")
@Composable
private fun FMAvatarDarkPreview() {
    FamilyMessengerTheme(darkTheme = true) {
        FMAvatarPreviewContent()
    }
}

@Composable
private fun FMAvatarPreviewContent() {
    androidx.compose.material3.Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        androidx.compose.foundation.layout.Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FMAvatar(
                imageUrl = null,
                initials = "EL",
                size = 56.dp
            )

            FMAvatar(
                imageUrl = null,
                initials = "M",
                size = 48.dp
            )

            FMAvatar(
                imageUrl = null,
                initials = null,
                size = 40.dp
            )
        }
    }
}
