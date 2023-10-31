package com.petluu.app.feature_home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.petluu.app.feature_home.domain.Reminder
import com.petluu.app.feature_home.presentation.components.PetListItem
import com.petluu.app.feature_home.presentation.components.ReminderListItem
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

/**
 * @author Cedierick Vyron Arediano
 * @since 1.0.0
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
    imagePicker: ImagePicker,
    onPetSelected: () -> Unit,
    onAddNewPetClick: () -> Unit
) {
    imagePicker.registerPicker { imageBytes ->
        onEvent(HomeEvent.OnPhotoPicked(imageBytes))
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { _ ->
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(bottom = paddingValues.calculateBottomPadding())
        ) {
            item {
                GreetingNameSection()

                Spacer(Spacing.Vertical.MD.heightModifier)

                PetListSection(
                    onEvent,
                    Modifier.fillMaxWidth(),
                    state,
                    onPetSelected,
                    onAddNewPetClick
                )

                AddHeaderSection(
                    headerTitle = "To take",
                    onAddClick = {
                        //TODO onEvent OnSeeAllClick onEvent(PetListEvent.OnAddNewReminder)
                    },
                    onSeeAllClick = {
                        //TODO onEvent OnSeeAllClick
                    }
                )
            }

            val hardcodedReminders = listOf(
                Reminder(1, "NextGard Spectra 30-40kg", 0, "Flea & tick treatment", "Bron"),
                Reminder(2, "4 in 1 Vaccine", 0, "Vaccination", "Coby"),
                Reminder(3, "Deworming", 0, "Deworming", "Coby"),
                Reminder(4, "Flea & tick treatment", 0, "Flea & tick treatment", "Coby"),
            )
            items(hardcodedReminders) { reminder ->
                ReminderListItem(reminder = reminder)
            }
        }

        Spacer(Spacing.Vertical.MD.heightModifier)
    }
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
private fun PetListSection(
    onEvent: (HomeEvent) -> Unit,
    modifier: Modifier,
    state: HomeState,
    onPetSelected: () -> Unit,
    onAddNewPetClick: () -> Unit
) {
    AddHeaderSection(
        headerTitle = "Your pets",
        onAddClick = { onAddNewPetClick() },
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
                    onEvent(HomeEvent.SelectPet(pet))

                    onPetSelected()
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