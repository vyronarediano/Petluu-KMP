package com.petluu.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.petluu.app.core.presentation.ImagePicker
import com.petluu.app.core.presentation.PetluuTheme
import com.petluu.app.di.AppModule
import com.petluu.app.feature_home.presentation.HomeVM
import com.petluu.app.ui.navigation.PetluuNavigation
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory

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
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            PetluuNavigation(viewModel, imagePicker)
        }
    }
}