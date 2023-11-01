package com.petluu.app.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.petluu.app.core.presentation.ImagePicker
import com.petluu.app.core.presentation.util.Dimens
import com.petluu.app.feature_home.presentation.HomeScreen
import com.petluu.app.feature_home.presentation.HomeVM
import com.petluu.app.feature_home.presentation.PetDetailScreen
import com.petluu.app.feature_home.presentation.components.AddPetSheet
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class,
    ExperimentalMaterialApi::class
)
@Composable
actual fun PetluuNavigation(viewModel: HomeVM, imagePicker: ImagePicker) {

    /**
     * TODO Implement Nav Graph to be able to fix the backstack when accessing PetDetail screen and navigate to setting nav bar and goes back to home,
     * TODO it should retain the state if PetDetail screen
     */

    val navController = rememberAnimatedNavController()
    val state by viewModel.state.collectAsState()

    val scope = rememberCoroutineScope()
    val skipHalfExpanded by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = skipHalfExpanded
    )

    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxSize(),
        sheetShape = RoundedCornerShape(
            topEnd = Dimens.BottomSheet.bottomSheetRadius,
            topStart = Dimens.BottomSheet.bottomSheetRadius
        ),
        sheetState = sheetState,
        sheetContent = {
            AddPetSheet(
                state = state,
                newPet = viewModel.newPet,
                isOpen = state.isAddPetSheetOpen,
                onEvent = viewModel::onEvent,
                onAddPhotoClicked = {
                    imagePicker.pickImage()
                }
            )
        }
    ) {
        Scaffold(
            bottomBar = { BottomBarNav(navController) }
        ) { paddingValues ->
            AnimatedNavHost(
                navController = navController,
                startDestination = Screen.Home.route,
            ) {
                //region HOME ROUTE
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
                        paddingValues = paddingValues,
                        state = state,
                        imagePicker = imagePicker,
                        onEvent = viewModel::onEvent,
                        onPetSelected = {
                            navController.navigate(Screen.PetDetail.route)
                        },
                        onAddNewPetClick = {
                            scope.launch {
                                sheetState.show()
                            }
                        },
                    )
                }
                //endregion HOME ROUTE

                //region SETTINGS ROUTE
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
                //endregion SETTINGS ROUTE

                petDetailsNavGraph(navController, viewModel)
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.petDetailsNavGraph(
    navController: NavHostController,
    viewModel: HomeVM
) {
    navigation(
        route = Graph.PET_DETAILS,
        startDestination = Screen.PetDetail.route
    ) {
        //region PET DETAIL ROUTE
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
                onBackClick = { navController.navigateUp() }
            )
        }
        //endregion PET DETAIL ROUTE
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBarNav(navController: NavHostController) {
    val screens = listOf(
        BottomNavigationItem.Home,
        BottomNavigationItem.Settings
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        screens.forEachIndexed { _, screen ->
            val backStackEntry = navController.currentBackStackEntryAsState()
            val selected = screen.route == backStackEntry.value?.destination?.route
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                },
                label = {
                    Text(
                        text = screen.title,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                },
                alwaysShowLabel = true,
                icon = {
                    BadgedBox(
                        badge = {
                            if (screen.badgeCount != null) {
                                Badge {
                                    Text(text = screen.badgeCount.toString())
                                }
                            } else if (screen.hasNews) {
                                Badge()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (selected) {
                                screen.selectedIcon
                            } else screen.unselectedIcon,
                            contentDescription = screen.title
                        )
                    }
                }
            )
        }
    }
}