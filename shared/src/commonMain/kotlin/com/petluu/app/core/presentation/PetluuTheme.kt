package com.petluu.app.core.presentation

import androidx.compose.runtime.Composable

@Composable
expect fun PetluuTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable () -> Unit
)