
package com.elenaibraeva.familymessenger.feature.chatlist.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elenaibraeva.familymessenger.generated.resources.Res
import com.elenaibraeva.familymessenger.generated.resources.ic_close
import com.elenaibraeva.familymessenger.generated.resources.ic_search
import org.jetbrains.compose.resources.painterResource

private val SearchBarShape = RoundedCornerShape(16.dp)

@Composable
fun ChatSearchBar(query: String, onQueryChange: (String) -> Unit, modifier: Modifier = Modifier) {
    var isFocused by remember { mutableStateOf(false) }

    ChatSearchBarContent(
        query = query,
        onQueryChange = onQueryChange,
        isActive = isFocused,
        onFocusChanged = { isFocused = it },
        modifier = modifier
    )
}

@Composable
private fun ChatSearchBarContent(
    query: String,
    onQueryChange: (String) -> Unit,
    isActive: Boolean,
    onFocusChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme

    val accentColor = if (isActive) {
        colors.primary
    } else {
        colors.onSurfaceVariant
    }

    val borderColor = if (isActive) {
        colors.primary
    } else {
        Color.Transparent
    }

    val backgroundColor = colors.surfaceContainerLow

    BasicTextField(
        value = query,
        onValueChange = onQueryChange,
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyLarge.merge(
            TextStyle(color = colors.onSurface)
        ),
        cursorBrush = androidx.compose.ui.graphics.SolidColor(colors.primary),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { onFocusChanged(it.isFocused) },
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 52.dp)
                    .background(backgroundColor, SearchBarShape)
                    .border(
                        width = if (isActive) 2.dp else 1.dp,
                        color = borderColor,
                        shape = SearchBarShape
                    )
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_search),
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(24.dp)
                )

                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (query.isEmpty()) {
                        Text(
                            text = "Search chats",
                            style = MaterialTheme.typography.bodyLarge,
                            color = colors.onSurfaceVariant
                        )
                    }

                    innerTextField()
                }

                if (query.isNotEmpty()) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_close),
                        contentDescription = "Clear search",
                        tint = accentColor,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable(
                                interactionSource = remember {
                                    MutableInteractionSource()
                                },
                                indication = null,
                                onClick = { onQueryChange("") }
                            )
                    )
                }
            }
        }
    )
}

@Preview(name = "Search - Inactive", showBackground = true)
@Composable
private fun ChatSearchInactivePreview() {
    MaterialTheme {
        ChatSearchBarContent(
            query = "",
            onQueryChange = {},
            isActive = false,
            onFocusChanged = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(name = "Search - Active Empty", showBackground = true)
@Composable
private fun ChatSearchActiveEmptyPreview() {
    MaterialTheme {
        ChatSearchBarContent(
            query = "",
            onQueryChange = {},
            isActive = true,
            onFocusChanged = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(name = "Search - Active With Query", showBackground = true)
@Composable
private fun ChatSearchActiveWithQueryPreview() {
    MaterialTheme {
        ChatSearchBarContent(
            query = "Anna",
            onQueryChange = {},
            isActive = true,
            onFocusChanged = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
