package com.maheswara660.vidora.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.maheswara660.vidora.settings.screens.gesture.GesturePreferencesScreen

const val gesturePreferencesNavigationRoute = "gesture_preferences_route"

fun NavController.navigateToGesturePreferences(navOptions: NavOptions? = navOptions { launchSingleTop = true }) {
    this.navigate(gesturePreferencesNavigationRoute, navOptions)
}

fun NavGraphBuilder.gesturePreferencesScreen(onNavigateUp: () -> Unit) {
    composable(route = gesturePreferencesNavigationRoute) {
        GesturePreferencesScreen(onNavigateUp = onNavigateUp)
    }
}
