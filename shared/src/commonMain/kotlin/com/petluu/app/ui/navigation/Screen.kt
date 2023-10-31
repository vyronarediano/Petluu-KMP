package com.petluu.app.ui.navigation

sealed class Screen(open val route: String = "") {
    object Home : Screen("home")
    object PetDetail : Screen("pet_detail")
    object Settings : Screen("settings")
}