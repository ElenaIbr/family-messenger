
package com.elenaibraeva.familymessenger.feature.chatlist.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elenaibraeva.familymessenger.generated.resources.Res
import com.elenaibraeva.familymessenger.generated.resources.ic_create_chat
import org.jetbrains.compose.resources.painterResource
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CreateChatFab(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    visible: Boolean = true,
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(220)) +
                scaleIn(
                    initialScale = 0.8f,
                    animationSpec = tween(220),
                ),
        exit = fadeOut(animationSpec = tween(150)) +
                scaleOut(
                    targetScale = 0.8f,
                    animationSpec = tween(150),
                ),
        modifier = modifier,
    ) {
        FloatingActionButton(
            onClick = onClick,
            shape = RoundedCornerShape(16.dp),
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_create_chat),
                contentDescription = "Create new chat",
            )
        }
    }
}

@Preview(name = "Create Chat FAB - Light", showBackground = true)
@Composable
private fun CreateChatFabLightPreview() {
    MaterialTheme {
        Box(modifier = Modifier.padding(24.dp)) {
            CreateChatFab(
                onClick = {},
            )
        }
    }
}

@Preview(name = "Create Chat FAB - Dark", showBackground = true)
@Composable
private fun CreateChatFabDarkPreview() {
    MaterialTheme(
        colorScheme = androidx.compose.material3.darkColorScheme(),
    ) {
        Box(modifier = Modifier.padding(24.dp)) {
            CreateChatFab(
                onClick = {},
            )
        }
    }
}

@Preview(name = "Create Chat FAB - Interactive")
@Composable
private fun CreateChatFabInteractivePreview() {
    var visible by remember { mutableStateOf(true) }

    MaterialTheme {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            CreateChatFab(
                onClick = {},
                visible = visible,
            )

            Button(
                onClick = { visible = !visible },
            ) {
                Text(if (visible) "Hide FAB" else "Show FAB")
            }
        }
    }
}
