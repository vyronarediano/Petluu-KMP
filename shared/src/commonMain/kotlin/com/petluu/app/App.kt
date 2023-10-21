package com.petluu.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.petluu.app.core.presentation.ImagePicker
import com.petluu.app.core.presentation.PetluuTheme
import com.petluu.app.di.AppModule
import com.petluu.app.feature_home.presentation.HomeScreen
import com.petluu.app.feature_home.presentation.HomeVM
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory

@ExperimentalMaterial3Api
@Composable
fun App(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    appModule: AppModule,
    imagePicker: ImagePicker
) {
    PetluuTheme(
        darkTheme = darkTheme,
        dynamicColor = dynamicColor
    ) {
        val viewModel = getViewModel(
            key = "pet-list-screen",
            factory = viewModelFactory {
                HomeVM(appModule.petDataSource)
            }
        )
        val state by viewModel.state.collectAsState()
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            HomeScreen(
                state = state,
                newPet = viewModel.newPet,
                onEvent = viewModel::onEvent,
                imagePicker = imagePicker
            )
        }
    }
}