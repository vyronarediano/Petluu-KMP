package com.petluu.app.feature_home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.petluu.app.core.presentation.BottomSheetFromWish
import com.petluu.app.core.presentation.util.Dimens
import com.petluu.app.core.presentation.util.Padding
import com.petluu.app.core.presentation.util.Spacing
import com.petluu.app.core.presentation.util.Spacing.All.heightModifier
import com.petluu.app.feature_home.domain.Pet
import com.petluu.app.feature_home.presentation.HomeEvent

@Composable
fun PetDetailSheet(
    isOpen: Boolean,
    selectedPet: Pet?,
    onEvent: (HomeEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    BottomSheetFromWish(
        visible = isOpen,
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopStart
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(60.dp))
                PetPhoto(
                    pet = selectedPet,
                    iconSize = 50.dp,
                    modifier = Modifier.size(150.dp)
                )
                Spacer(Spacing.Vertical.MD.heightModifier)
                Text(
                    text = "${selectedPet?.name}",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )
                Spacer(Spacing.Vertical.MD.heightModifier)
                EditRow(
                    onEditClick = {
                        selectedPet?.let {
                            onEvent(HomeEvent.EditPet(it))
                        }
                    },
                    onDeleteClick = {
                        onEvent(HomeEvent.DeletePet)
                    }
                )
                Spacer(Spacing.Vertical.MD.heightModifier)
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = Padding.Horizontal.XS),
                    horizontalArrangement = Arrangement.spacedBy(Spacing.Horizontal.MD),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PetInfo(
                        modifier = Modifier.weight(1f),
                        petInfoValue = "6 years",
                        helperLabel = "Age"
                    )

                    //TODO Harcoded for now
                    val unitsOfWeight = listOf("lg", "kg")
                    PetInfo(
                        modifier = Modifier.weight(1f),
                        petInfoValue = "${selectedPet?.weight} ${unitsOfWeight.first()}",
                        helperLabel = "Weight"
                    )
                    PetInfo(
                        modifier = Modifier.weight(1f),
                        petInfoValue = selectedPet?.gender?.name?.lowercase()?.capitalize().orEmpty(),
                        helperLabel = "Gender"
                    )
                }
            }

            IconButton(
                onClick = {
                    onEvent(HomeEvent.DismissPet)
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "Close"
                )
            }
        }
    }
}

@Composable
private fun PetInfo(
    modifier: Modifier = Modifier,
    petInfoValue: String,
    helperLabel: String
) {
    Box(
        modifier = modifier
            .clip(
                RoundedCornerShape(Dimens.RoundedCornerRadius.large)
            )
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(vertical = Padding.Vertical.LG, horizontal = Padding.Horizontal.MD),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = petInfoValue,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontSize = 18.sp
            )

            Spacer(Spacing.Vertical.SM.heightModifier)

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = helperLabel,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
private fun EditRow(
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        FilledTonalIconButton(
            onClick = onEditClick,
            colors = IconButtonDefaults.filledTonalIconButtonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
        ) {
            Icon(
                imageVector = Icons.Rounded.Edit,
                contentDescription = "Edit contact"
            )
        }
        FilledTonalIconButton(
            onClick = onDeleteClick,
            colors = IconButtonDefaults.filledTonalIconButtonColors(
                containerColor = MaterialTheme.colorScheme.errorContainer,
                contentColor = MaterialTheme.colorScheme.onErrorContainer
            )
        ) {
            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = "Delete Pet"
            )
        }
    }
}