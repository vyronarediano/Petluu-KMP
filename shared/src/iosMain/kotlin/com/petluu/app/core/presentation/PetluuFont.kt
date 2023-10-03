package com.petluu.app.core.presentation

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.platform.Typeface
import org.jetbrains.skia.FontStyle
import org.jetbrains.skia.Typeface

private fun loadCustomFont(name: String): Typeface {
    return Typeface.makeFromName(name, FontStyle.NORMAL)
}

actual val lexendFontFamily: FontFamily = FontFamily(
    Typeface(loadCustomFont("lexend")),
)

actual val openSansFontFamily: FontFamily = FontFamily(
    Typeface(loadCustomFont("open_sans"))
)

