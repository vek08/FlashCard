package com.flashcard.app.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.flashcard.app.presentation.addEdit.AddEditScreen
import com.flashcard.app.presentation.home.HomeScreen


@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
    ) {
        
        composable(
            route = Screen.Home.route,
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) }
        ) {
            HomeScreen(
                onNavigateToAdd = {
                    navController.navigate(Screen.AddFlashcard.route)
                },
                onNavigateToEdit = { flashcardId ->
                    navController.navigate(Screen.EditFlashcard.createRoute(flashcardId))
                }
            )
        }

        
        composable(
            route = Screen.AddFlashcard.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(350)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(350)
                )
            }
        ) {
            AddEditScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        
        composable(
            route = Screen.EditFlashcard.route,
            arguments = listOf(
                navArgument("flashcardId") { type = NavType.IntType }
            ),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(350)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(350)
                )
            }
        ) {
            AddEditScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
