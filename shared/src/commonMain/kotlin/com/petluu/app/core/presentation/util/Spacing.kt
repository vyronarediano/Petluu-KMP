package com.petluu.app.core.presentation.util

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

sealed class Spacing {

    val Dp.heightModifier: Modifier
        get() = Modifier.height(this)

    val Dp.widthModifier: Modifier
        get() = Modifier.width(this)

    object All : Spacing() {
        val XXS = 2.dp
        val XS = 4.dp
        val SM = 8.dp
        val MD = 16.dp
        val LG = 24.dp
        val XL = 32.dp
    }

    object Vertical : Spacing() {
        val XXS = 2.dp
        val XS = 4.dp
        val SM = 8.dp
        val MD = 16.dp
        val LG = 24.dp
        val XL = 32.dp
    }

    object Horizontal : Spacing() {
        val XXS = 2.dp
        val XS = 4.dp
        val SM = 8.dp
        val MD_12 = 12.dp
        val MD = 16.dp
        val LG = 24.dp
        val XL = 32.dp
        val XXL = 40.dp
    }
}
