package com.petluu.app.feature_home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import com.petluu.app.core.presentation.util.Dimens
import com.petluu.app.core.presentation.util.Padding
import com.petluu.app.core.presentation.util.Spacing
import com.petluu.app.core.presentation.util.Spacing.All.heightModifier
import com.petluu.app.feature_home.domain.Pet
import com.petluu.app.utils.AsyncImage

/**
 * @author Cedierick Vyron Arediano
 * @since 1.0.0
 */
@ExperimentalMaterial3Api
@Composable
fun PetListItem(
    pet: Pet,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(Dimens.RoundedCornerRadius.large),
        elevation = CardDefaults.cardElevation(
            defaultElevation = Dimens.Elevation.petListItemCardElevation
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        onClick = onClick,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.Card.petCardHeight)
        ) {
            val hardcodedImage =
                if (pet.name == "Bron") "https://www.dailypaws.com/thmb/DQfQglzyKWlVSlsDwKPprF2iMSg=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/golden-retriever-177213599-2000-a30830f4d2b24635a5d01b3c5c64b9ef.jpg"
                else "https://t3.ftcdn.net/jpg/00/33/76/64/360_F_33766426_7IWdoKFDhIDVFsaxC43zKH0LhyN5z8Kw.jpg"

            AsyncImage(
                imageUrl = hardcodedImage,
                contentDescription = "Pet Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.Image.petCardImageHeight)
                    .clip(RoundedCornerShape(Dimens.RoundedCornerRadius.large))
                    .background(Color.LightGray),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.fillMaxSize().padding(Padding.All.SM),
            ) {
                Spacer(Spacing.Vertical.SM.heightModifier)

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${pet.name}",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.weight(1f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )

                    Text(
                        text = "2 yrs",
                        style = MaterialTheme.typography.labelMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }

                Spacer(Spacing.Vertical.XS.heightModifier)

                Text(
                    text = "${pet.breed}",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.fillMaxWidth(1f),
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }


        }
    }

}