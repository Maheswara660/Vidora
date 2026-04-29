package com.maheswara660.vidora.navigation.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.toRoute
import com.maheswara660.vidora.feature.videopicker.navigation.HistoryRoute
import com.maheswara660.vidora.feature.videopicker.navigation.HomeRoute
import com.maheswara660.vidora.feature.videopicker.navigation.VideosRoute
import com.maheswara660.vidora.navigation.TopLevelDestination
import com.maheswara660.vidora.settings.navigation.SettingsRoute

@Composable
fun VidoraBottomBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    navBackStackEntry: NavBackStackEntry?,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier,
    ) {
        destinations.forEach { destination ->
            val selected = navBackStackEntry.isTopLevelDestinationInHierarchy(destination)
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        imageVector = if (selected) {
                            destination.selectedIcon
                        } else {
                            destination.unselectedIcon
                        },
                        contentDescription = null,
                    )
                },
                label = {
                    Text(stringResource(destination.iconTextId))
                },
            )
        }
    }
}

private fun NavBackStackEntry?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.destination?.hierarchy?.any { dest ->
        when (destination) {
            TopLevelDestination.HOME -> dest.hasRoute<HomeRoute>()
            TopLevelDestination.VIDEOS -> dest.hasRoute<VideosRoute>()
            TopLevelDestination.HISTORY -> dest.hasRoute<HistoryRoute>()
            TopLevelDestination.SETTINGS -> dest.hasRoute<SettingsRoute>()
        }
    } ?: false
