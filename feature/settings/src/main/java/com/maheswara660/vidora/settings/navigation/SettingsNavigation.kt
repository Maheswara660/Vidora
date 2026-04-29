package com.maheswara660.vidora.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.maheswara660.vidora.settings.Setting
import com.maheswara660.vidora.settings.SettingsScreen
import kotlinx.serialization.Serializable

@Serializable
data object SettingsRoute

fun NavController.navigateToSettings(navOptions: NavOptions? = navOptions { launchSingleTop = true }) {
    this.navigate(SettingsRoute, navOptions)
}

fun NavGraphBuilder.settingsScreen(onNavigateUp: () -> Unit, onItemClick: (Setting) -> Unit) {
    composable<SettingsRoute> {
        SettingsScreen(onNavigateUp = onNavigateUp, onItemClick = onItemClick)
    }
}
