package com.elenaibraeva.familymessenger

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.elenaibraeva.familymessenger.core.designsystem.theme.FamilyMessengerTheme

@Composable
fun App() {
    FamilyMessengerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Family Messenger",
                    style = MaterialTheme.typography.headlineLarge,
                )
            }
        }
    }
}
