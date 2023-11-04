package com.petluu.app.feature_home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.petluu.app.core.presentation.util.Dimens
import com.petluu.app.core.presentation.util.Padding
import com.petluu.app.core.presentation.util.Spacing
import com.petluu.app.core.presentation.util.Spacing.All.heightModifier
import com.petluu.app.feature_home.domain.Pet
import com.petluu.app.feature_home.presentation.components.PetCoverPhoto
import com.petluu.app.feature_home.presentation.components.PetPhoto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetDetailScreen(
    viewModel: HomeVM,
    onEditPetClick: (Pet) -> Unit,
    onDeletePetClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val selectedPet = viewModel.state.value.selectedPet

    Scaffold(
        topBar = {
            PetDetailTopAppBar(onBackClick)
        }
    ) { paddingValues ->
        selectedPet?.let { pet ->
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                //contentPadding = PaddingValues(top = paddingValues.calculateTopPadding()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Column {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            Column {
                                PetCoverPhoto(
                                    modifier = Modifier.height(140.dp).fillMaxWidth(),
                                    pet = pet,
                                    backgroundEmptyColor = MaterialTheme.colorScheme.outline,
                                    iconSize = 50.dp
                                )

                                Spacer(Spacing.Vertical.SM.heightModifier)

                                EditRow(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = Padding.Horizontal.MD),
                                    onEditClick = {
                                        onEditPetClick(pet)
                                    },
                                    onDeleteClick = {
                                        onDeletePetClick()
                                    }
                                )
                            }

                            Box(modifier = Modifier.padding(top = 100.dp)) {
                                PetPhoto(
                                    pet = pet,
                                    iconSize = 50.dp,
                                    modifier = Modifier
                                        .padding(start = Padding.Horizontal.MD)
                                        .size(100.dp)
                                )
                            }
                        }

                        Spacer(Spacing.Vertical.MD.heightModifier)

                        Text(
                            text = pet.name,
                            modifier = Modifier.padding(horizontal = Padding.Horizontal.MD + Padding.Horizontal.XS),
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp
                        )
                    }

                    Spacer(Spacing.Vertical.MD.heightModifier)

                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = Padding.Horizontal.MD),
                        horizontalArrangement = Arrangement.spacedBy(Spacing.Horizontal.MD),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        PetInfoVerticalItem(
                            modifier = Modifier.weight(1f),
                            petInfoValue = "6 years",
                            helperLabel = "Age"
                        )

                        //TODO Harcoded for now
                        val unitsOfWeight = listOf("lg", "kg")
                        PetInfoVerticalItem(
                            modifier = Modifier.weight(1f),
                            petInfoValue = "${pet.weight} ${unitsOfWeight.first()}",
                            helperLabel = "Weight"
                        )
                        PetInfoVerticalItem(
                            modifier = Modifier.weight(1f),
                            petInfoValue = pet.gender?.name?.lowercase()?.capitalize().orEmpty(),
                            helperLabel = "Gender"
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Padding.Horizontal.MD)
                            .clip(
                                RoundedCornerShape(Dimens.RoundedCornerRadius.large)
                            )
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .height(200.dp),
                        verticalArrangement = Arrangement.spacedBy(Padding.Vertical.SM)

                    ) {
                        Spacer(Spacing.Vertical.MD.heightModifier)

                        PetInfoHorizontalItem("Birthday", "May 18, 2020")
                        PetInfoHorizontalItem("Color", "Golden")
                        PetInfoHorizontalItem("Microchip Number", "104HD9393")

                    }
                }

            }
        }
    }
}

@Composable
private fun PetInfoHorizontalItem(helperLabel: String, petInfoValue: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = Padding.Horizontal.MD),
        horizontalArrangement = Arrangement.spacedBy(
            20.dp,
            Alignment.CenterHorizontally
        )
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = helperLabel,
            color = MaterialTheme.colorScheme.outline,
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            modifier = Modifier.weight(1f),
            text = petInfoValue,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun PetInfoVerticalItem(
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
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Spacing.Vertical.SM.heightModifier)

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = helperLabel,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                style = MaterialTheme.typography.bodyMedium
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
    Row(modifier, horizontalArrangement = Arrangement.End) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetDetailTopAppBar(
    onBackButtonClicked: () -> Unit
) {
    TopAppBar(
        title = { },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color.Transparent
        ),
        navigationIcon = {
            IconButton(
                onClick = { onBackButtonClicked() },
                modifier = Modifier
                    .padding(horizontal = Padding.Horizontal.SM)
                    .background(
                        color = Color.Black.copy(alpha = 0.05f),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBackIos,
                    contentDescription = "Back icon",
                    tint = Color.White
                )
            }
        },
        actions = {}
    )
}