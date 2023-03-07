package com.example.humblr.ui.compose.theming

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

private val LightColors = lightColorScheme(
    primary = Purple500,
    onPrimary = Color.White,
    secondary = Purple200,
    onSecondary = Color.White,
    error = Red800
)

private val DarkColors = darkColorScheme(
    primary = Purple500,
    onPrimary = Color.Black,
    secondary = Purple200,
    onSecondary = Color.Black,
    error = Red200
)

@Composable
fun CustomTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = CustomTypography,
        shapes = CustomShapes,
        content = content
    )
}