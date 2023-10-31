package com.petluu.app.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.petluu.app.core.presentation.ImagePicker
import com.petluu.app.feature_home.presentation.HomeScreen
import com.petluu.app.feature_home.presentation.HomeVM
import com.petluu.app.feature_home.presentation.PetDetailScreen

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
actual fun PetluuNavigation(viewModel: HomeVM, imagePicker: ImagePicker) {
    val navController = rememberAnimatedNavController()
    val state by viewModel.state.collectAsState()

    Scaffold(
        bottomBar = { BottomNavigation(navController) }
    ) { _ ->
        AnimatedNavHost(
            navController = navController,
            startDestination = Screen.Home.route,
        ) {
            composable(
                route = Screen.Home.route,
                enterTransition = {
                    when (initialState.destination.route) {
                        Screen.PetDetail.route ->
                            slideInHorizontally(
                                initialOffsetX = { 300 },
                                animationSpec = tween(300)
                            ) + fadeIn(animationSpec = tween(300))

                        else -> null
                    }
                },
                exitTransition = {
                    when (targetState.destination.route) {
                        Screen.PetDetail.route ->
                            slideOutHorizontally(
                                targetOffsetX = { -300 },
                                animationSpec = tween(300)
                            ) + fadeOut(animationSpec = tween(300))

                        else -> null
                    }
                },
                popEnterTransition = {
                    when (initialState.destination.route) {
                        Screen.PetDetail.route ->
                            slideInHorizontally(
                                initialOffsetX = { -300 },
                                animationSpec = tween(300)
                            ) + fadeIn(animationSpec = tween(300))

                        else -> null
                    }
                }
            ) {
                HomeScreen(
                    state = state,
                    newPet = viewModel.newPet,
                    imagePicker = imagePicker,
                    onEvent = viewModel::onEvent,
                    onPetSelected = {
                        navController.navigate(Screen.PetDetail.route)
                    }
                )
            }
            composable(
                route = Screen.PetDetail.route,
                enterTransition = {
                    when (initialState.destination.route) {
                        Screen.Home.route ->
                            slideInHorizontally(
                                initialOffsetX = { 300 },
                                animationSpec = tween(300)
                            ) + fadeIn(animationSpec = tween(300))

                        else -> null
                    }
                },
                exitTransition = {
                    when (targetState.destination.route) {
                        Screen.Home.route ->
                            slideOutHorizontally(
                                targetOffsetX = { -300 },
                                animationSpec = tween(300)
                            ) + fadeOut(animationSpec = tween(300))

                        else -> null
                    }
                },
                popExitTransition = {
                    when (targetState.destination.route) {
                        Screen.Home.route ->
                            slideOutHorizontally(
                                targetOffsetX = { 300 },
                                animationSpec = tween(300)
                            ) + fadeOut(animationSpec = tween(300))

                        else -> null
                    }
                }
            ) {
                PetDetailScreen(
                    viewModel = viewModel,
                    onEditPetClick = {

                    },
                    onDeletePetClick = {

                    },
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable(
                route = Screen.Settings.route,
                enterTransition = {
                    when (initialState.destination.route) {
                        Screen.Home.route ->
                            slideInHorizontally(
                                initialOffsetX = { 300 },
                                animationSpec = tween(300)
                            ) + fadeIn(animationSpec = tween(300))

                        else -> null
                    }
                },
                exitTransition = {
                    when (targetState.destination.route) {
                        Screen.Home.route ->
                            slideOutHorizontally(
                                targetOffsetX = { 300 },
                                animationSpec = tween(300)
                            ) + fadeOut(animationSpec = tween(300))

                        else -> null
                    }
                },
                popExitTransition = {
                    when (targetState.destination.route) {
                        Screen.Home.route ->
                            slideOutHorizontally(
                                targetOffsetX = { 300 },
                                animationSpec = tween(300)
                            ) + fadeOut(animationSpec = tween(300))

                        else -> null
                    }
                }
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Settings screen here",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigation(navController: NavController) {
    val navigationItems = listOf(
        BottomNavigationItem.Home,
        BottomNavigationItem.Settings
    )
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }
    NavigationBar() {
        navigationItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index

                    navController.navigate(item.route)
                },
                label = {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                },
                alwaysShowLabel = true,
                icon = {
                    BadgedBox(
                        badge = {
                            if (item.badgeCount != null) {
                                Badge {
                                    Text(text = item.badgeCount.toString())
                                }
                            } else if (item.hasNews) {
                                Badge()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (index == selectedItemIndex) {
                                item.selectedIcon
                            } else item.unselectedIcon,
                            contentDescription = item.title
                        )
                    }
                }
            )
        }
    }
}