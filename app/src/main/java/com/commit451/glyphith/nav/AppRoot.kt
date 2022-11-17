package com.commit451.glyphith.nav

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.commit451.glyphith.CreateScreen
import com.commit451.glyphith.DebugScreen
import com.commit451.glyphith.MainScreen
import com.commit451.glyphith.SettingsScreen
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
    NavHost(
        navController = navController,
        startDestination = Screen.Main.name,
        modifier = modifier
    ) {
        val onBack = {
            navController.popBackStack()
            Unit
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
        composable(Screen.Create.name) { CreateScreen() }
        composable(Screen.Debug.name) { DebugScreen(onBack) }
    }
}