package com.petluu.app.core.presentation.util

import androidx.compose.ui.unit.dp

sealed class Dimens {

    object Button : Dimens() {

        val addPetBtnSize = 38.dp

        val backBtnSize = 45.dp

    }

    object TextField : Dimens() {

        val tfDefaultHeight = 18.dp

    }

    object Icon : Dimens() {

        val imageUserPlaceholderIconSize = 20.dp

    }

    object Image : Dimens() {

        val petCardImageHeight = 120.dp

    }

    object BottomSheet : Dimens() {

        val bottomSheetRadius = 30.dp
    }

    object BorderStroke : Dimens() {

        val difficultySelected = 2.dp

    }

    object RoundedCornerRadius : Dimens() {

        val small = 4.dp

        val medium = 8.dp

        val large = 16.dp

        val extraLarge = 30.dp
    }

    object DragHandle : Dimens() {

        val dragHandleWidth = 32.dp

        val dragHandleHeight = 4.dp
    }

    object Elevation : Dimens() {

        val petListItemCardElevation = 5.dp
    }

    object Tab : Dimens() {

        val profileTabHeight = 50.dp
    }

    object Row : Dimens() {

        val accountSettingsRowHeight = 90.dp

        val settingsHelpRowHeight = 70.dp
    }

    object Section : Dimens() {

        val recipeCardTopSectionHeight = 150.dp
    }

    object Box : Dimens() {

        val recipeCardTagBadgeHeight = 30.dp
    }

    object Card : Dimens() {

        val petCardHeight = 190.dp

        val reminderCardHeight = 100.dp
    }
}
