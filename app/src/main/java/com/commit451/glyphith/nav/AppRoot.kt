package com.commit451.glyphith.nav

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.commit451.glyphith.*
import com.commit451.glyphith.data.Prefs
import com.commit451.glyphith.ui.GlyphithTheme

@Composable
fun AppRoot(context: Context) {
    val navController = rememberNavController()
    GlyphithTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NavHost(context, navController)
        }
    }
}

@Composable
fun NavHost(context: Context, navController: NavHostController, modifier: Modifier = Modifier) {
    val start = if (Prefs.hasSeenIntro) {
        Screen.Main.name
    } else {
        Screen.Intro.name
    }
    NavHost(
        navController = navController,
        startDestination = start,
        modifier = modifier
    ) {
        val onBack = {
            navController.popBackStack()
            Unit
        }
        composable(Screen.Intro.name) {
            IntroScreen(
                context = context,
                onMoveOn = {
                    navController.popBackStack()
                    navController.navigate(Screen.Main.name)
                }
            )
        }
        composable(Screen.Main.name) {
            MainScreen(
                context = context,
                navigateToSettings = {
                    navController.navigate(Screen.Settings.name)
                },
                navigateToCreate = {
                    navController.navigate(Screen.Create.name)
                },
                navigateToDebug = {
                    navController.navigate(Screen.Debug.name)
                }
            )
        }
        composable(Screen.Settings.name) { SettingsScreen(context, onBack) }
        composable(Screen.Create.name) {
            val viewModel: GlyphithViewModel = viewModel()
            CreateScreen(viewModel, onBack)
        }
        composable(Screen.Debug.name) { DebugScreen(onBack) }
    }
}