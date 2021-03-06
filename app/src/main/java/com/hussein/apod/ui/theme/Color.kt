package com.hussein.apod.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)
val MainBackgroundColor
    @Composable
    get() = if (isSystemInDarkTheme()) Color.Black else Color.LightGray.copy(alpha = 0.6f)

val CardOutlineColor
    @Composable
    get() = if (isSystemInDarkTheme()) Color.White.copy(alpha = .6f) else Color.LightGray.copy(alpha = 0.6f)