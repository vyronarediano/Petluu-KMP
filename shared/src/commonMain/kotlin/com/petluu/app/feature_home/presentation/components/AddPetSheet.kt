package com.petluu.app.feature_home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.petluu.app.core.presentation.BottomSheetFromWish
import com.petluu.app.core.presentation.util.Spacing
import com.petluu.app.core.presentation.util.Spacing.All.heightModifier
import com.petluu.app.feature_home.domain.Pet
import com.petluu.app.feature_home.presentation.HomeEvent
import com.petluu.app.feature_home.presentation.HomeState

@Composable
fun AddPetSheet(
    state: HomeState,
    newPet: Pet?,
    isOpen: Boolean,
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
                if (newPet?.photoBytes == null) {
                    Box(
                        modifier = Modifier
                            .size(150.dp)
                            .clip(RoundedCornerShape(40))
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .clickable {
                                onEvent(HomeEvent.OnAddPhotoClicked)
                            }
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                shape = RoundedCornerShape(40)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = "Add photo",
                            tint = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                } else {
                    PetPhoto(
                        pet = newPet,
                        modifier = Modifier
                            .size(150.dp)
                            .clickable {
                                onEvent(HomeEvent.OnAddPhotoClicked)
                            }
                    )
                }
                Spacer(Spacing.Vertical.MD.heightModifier)
                PetTextField(
                    value = newPet?.name ?: "",
                    placeholder = "Name",
                    error = state.nameError,
                    onValueChanged = {
                        onEvent(HomeEvent.OnNameChanged(it))
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Spacing.Vertical.MD.heightModifier)
                PetTextField(
                    value = newPet?.species ?: "",
                    placeholder = "Species",
                    error = state.speciesError,
                    onValueChanged = {
                        onEvent(HomeEvent.OnSpeciesChanged(it))
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Spacing.Vertical.MD.heightModifier)
                PetTextField(
                    value = newPet?.breed ?: "",
                    placeholder = "Breed",
                    error = state.breedError,
                    onValueChanged = {
                        onEvent(HomeEvent.OnBreedChanged(it))
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Spacing.Vertical.MD.heightModifier)
                //TODO change Gender to dropdown
                PetTextField(
                    value = newPet?.gender?.name?.capitalize() ?: "",
                    placeholder = "Gender",
                    error = null,
                    onValueChanged = {
                        onEvent(HomeEvent.OnGenderChanged(it))
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = {
                        onEvent(HomeEvent.SavePet)
                    }
                ) {
                    Text(text = "Save")
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PetTextField(
    value: String,
    placeholder: String,
    error: String?,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        OutlinedTextField(
            value = value,
            placeholder = {
                Text(text = placeholder)
            },
            onValueChange = onValueChanged,
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth()
        )
        if (error != null) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}