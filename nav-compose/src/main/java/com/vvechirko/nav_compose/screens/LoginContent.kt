package com.vvechirko.nav_compose.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LoginContent(
    onClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onForgotClick: () -> Unit
) {
    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier.clickable { onClick() },
                text = "LOGIN",
                style = MaterialTheme.typography.displaySmall
            )
            Text(
                modifier = Modifier.clickable { onSignUpClick() },
                text = "Sign Up",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                modifier = Modifier.clickable { onForgotClick() },
                text = "Forgot Password",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}