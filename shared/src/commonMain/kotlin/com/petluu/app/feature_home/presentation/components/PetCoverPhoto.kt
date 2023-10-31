package com.petluu.app.feature_home.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Pets
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.petluu.app.core.presentation.rememberBitmapFromBytes
import com.petluu.app.feature_home.domain.Pet

/**
 * @author Cedierick Vyron Arediano
 * @since 1.0.0
 */
@Composable
fun PetCoverPhoto(
    modifier: Modifier = Modifier,
    pet: Pet?,
    backgroundEmptyColor: Color,
    iconSize: Dp = 25.dp
) {
    val bitmap = rememberBitmapFromBytes(pet?.photoBytes)
    if (bitmap != null) {
        Image(
            bitmap = bitmap,
            contentDescription = pet?.name,
            modifier = modifier,
            contentScale = ContentScale.Crop
        )
    } else {
        Box(
            modifier = modifier.background(backgroundEmptyColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.Pets,
                contentDescription = pet?.name,
                modifier = Modifier.size(iconSize),
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}