package com.petluu.app.ui.navigation

import androidx.compose.runtime.Composable
import com.petluu.app.core.presentation.ImagePicker
import com.petluu.app.feature_home.presentation.HomeVM


@Composable
expect fun PetluuNavigation(viewModel: HomeVM, imagePicker: ImagePicker)
