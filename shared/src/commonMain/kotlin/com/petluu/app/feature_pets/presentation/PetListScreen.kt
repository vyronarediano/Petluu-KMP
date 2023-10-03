package com.petluu.app.feature_pets.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.petluu.app.core.presentation.ImagePicker
import com.petluu.app.core.presentation.util.Dimens
import com.petluu.app.core.presentation.util.Padding
import com.petluu.app.core.presentation.util.Spacing
import com.petluu.app.core.presentation.util.Spacing.Vertical.heightModifier
import com.petluu.app.feature_pets.domain.Pet
import com.petluu.app.feature_pets.domain.Reminder
import com.petluu.app.feature_pets.presentation.components.AddPetSheet
import com.petluu.app.feature_pets.presentation.components.PetDetailSheet
import com.petluu.app.feature_pets.presentation.components.PetListItem
import com.petluu.app.feature_pets.presentation.components.ReminderListItem
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

/**
 * @author Cedierick Vyron Arediano
 * @since 1.0.0
 */
@ExperimentalMaterial3Api
@Composable
fun PetListScreen(
    state: PetListState,
    newPet: Pet?,
    onEvent: (PetListEvent) -> Unit,
    imagePicker: ImagePicker
) {
    imagePicker.registerPicker { imageBytes ->
        onEvent(PetListEvent.OnPhotoPicked(imageBytes))
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            GreetingNameSection()

            Spacer(Spacing.Vertical.MD.heightModifier)

            HomeContent(modifier = Modifier.fillMaxWidth(), onEvent, state)

            Spacer(Spacing.Vertical.MD.heightModifier)
        }
    }

    PetDetailSheet(
        isOpen = state.isSelectedPetSheetOpen,
        selectedPet = state.selectedPet,
        onEvent = onEvent
    )

    AddPetSheet(
        state = state,
        newPet = newPet,
        isOpen = state.isAddPetSheetOpen,
        onEvent = { event ->
            if (event is PetListEvent.OnAddPhotoClicked) {
                imagePicker.pickImage()
            }
            onEvent(event)
        }
    )

}

@Composable
private fun GreetingNameSection() {
    Column(
        modifier = Modifier.padding(
            horizontal = Padding.Horizontal.MD,
            vertical = Padding.Vertical.MD
        )
    ) {
        Text(
            text = getGreeting(),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Start
        )

        Spacer(Spacing.Vertical.XS.heightModifier)

        Text(
            text = "Vyron!",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Start
        )
    }
}

@ExperimentalMaterial3Api
@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    onEvent: (PetListEvent) -> Unit,
    state: PetListState
) {
    LazyColumn (
        modifier = Modifier.fillMaxSize(),
    ) {
        item {
            Column {
                PetListSection(onEvent, modifier, state)

                AddHeaderSection(
                    headerTitle = "To take",
                    onAddClick = { onEvent(PetListEvent.OnAddNewPetClick) },
                    onSeeAllClick = {
                        //TODO onEvent OnSeeAllClick
                    }
                )
            }
        }

        val hardcodedReminders = listOf(
            Reminder(1, "NextGard Spectra 30-40kg", 0, "Flea & tick treatment", "Bron"),
            Reminder(2, "4 in 1 Vaccine", 0, "Vaccination", "Coby")
        )
        items(hardcodedReminders) { reminder ->
            ReminderListItem(reminder = reminder)
        }
    }
}

@ExperimentalMaterial3Api
@Composable
private fun PetListSection(
    onEvent: (PetListEvent) -> Unit,
    modifier: Modifier,
    state: PetListState
) {
    AddHeaderSection(
        headerTitle = "Your pets",
        onAddClick = { onEvent(PetListEvent.OnAddNewPetClick) },
        onSeeAllClick = {
            //TODO onEvent OnSeeAllClick
        }
    )

    val lazyListState = rememberLazyListState()
    LazyRow(
        contentPadding = PaddingValues(
            horizontal = Padding.Horizontal.MD, vertical = Padding.Vertical.SM
        ),
        modifier = modifier,
        state = lazyListState,
        horizontalArrangement = Arrangement.spacedBy(Spacing.Horizontal.SM)
    ) {
        items(state.pets) { pet ->
            PetListItem(
                pet = pet,
                modifier = Modifier.width(160.dp),
                onClick = {
                    onEvent(PetListEvent.SelectPet(pet))
                }
            )
        }
    }
}

@Composable
private fun AddHeaderSection(
    headerTitle: String,
    onAddClick: () -> Unit,
    onSeeAllClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Padding.Horizontal.MD, vertical = Padding.Vertical.SM),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.Horizontal.SM)
        ) {
            Text(
                text = headerTitle,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface
            )

            IconButton(
                onClick = { onAddClick() },
                modifier = Modifier
                    .size(Dimens.Button.addPetBtnSize)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = CircleShape
                    )
                    .clip(CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
        }

        Text(
            modifier = Modifier.clickable { onSeeAllClick() },
            text = "See all",
            style = MaterialTheme.typography.labelLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

private fun getGreeting(): String {
    val currentHour = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).hour
    return when {
        currentHour < 12 -> "Good Morning,"
        currentHour < 17 -> "Good Afternoon,"
        else -> "Good Evening,"
    }
}