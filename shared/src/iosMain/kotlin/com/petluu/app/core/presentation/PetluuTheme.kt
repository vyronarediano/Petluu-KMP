package com.petluu.app.core.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.petluu.app.ui.theme.DarkColorScheme
import com.petluu.app.ui.theme.LightColorScheme
import com.petluu.app.ui.theme.Typography

@Composable
actual fun PetluuTheme(
    darkTheme: Boolean,
    dynamicColor:   Boolean,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if(darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}