package com.maheswara660.vidora.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.maheswara660.vidora.settings.screens.thumbnail.ThumbnailPreferencesScreen

const val thumbnailPreferencesNavigationRoute = "thumbnail_preferences_route"

fun NavController.navigateToThumbnailPreferencesScreen(
    navOptions: NavOptions? = navOptions { launchSingleTop = true },
) {
    this.navigate(thumbnailPreferencesNavigationRoute, navOptions)
}

fun NavGraphBuilder.thumbnailPreferencesScreen(onNavigateUp: () -> Unit) {
    composable(route = thumbnailPreferencesNavigationRoute) {
        ThumbnailPreferencesScreen(onNavigateUp = onNavigateUp)
    }
}
