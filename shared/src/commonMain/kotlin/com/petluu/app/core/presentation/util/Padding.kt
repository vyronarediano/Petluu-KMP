package com.petluu.app.core.presentation.util

import androidx.compose.ui.unit.dp

sealed class Padding {

    object All : Padding() {
        val XXS = 2.dp
        val XS = 4.dp
        val SM = 8.dp
        val MD_10 = 12.dp
        val MD = 16.dp
        val LG = 24.dp
        val XL = 32.dp
    }

    object Vertical : Padding() {
        val XXS = 2.dp
        val XS = 4.dp
        val SM = 8.dp
        val MD = 16.dp
        val LG = 24.dp
        val XL = 32.dp
    }

    object Horizontal : Padding() {
        val XXS = 2.dp
        val XS = 4.dp
        val SM = 8.dp
        val MD = 16.dp
        val LG = 24.dp
        val XL = 32.dp
    }
}
