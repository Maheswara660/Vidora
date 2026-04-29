package com.maheswara660.vidora.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.maheswara660.vidora.core.ui.designsystem.VidoraIcons
import com.maheswara660.vidora.core.ui.R
import com.maheswara660.vidora.feature.videopicker.navigation.MediaPickerRoute
import com.maheswara660.vidora.feature.videopicker.navigation.MediaPickerScreenType
import com.maheswara660.vidora.feature.videopicker.navigation.HistoryRoute
import com.maheswara660.vidora.feature.videopicker.navigation.HomeRoute
import com.maheswara660.vidora.feature.videopicker.navigation.VideosRoute
import com.maheswara660.vidora.settings.navigation.SettingsRoute

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int,
    val route: Any,
) {
    HOME(
        selectedIcon = VidoraIcons.Folder,
        unselectedIcon = VidoraIcons.Folder,
        iconTextId = R.string.home,
        route = HomeRoute,
    ),
    VIDEOS(
        selectedIcon = VidoraIcons.Player,
        unselectedIcon = VidoraIcons.Player,
        iconTextId = R.string.videos,
        route = VideosRoute,
    ),
    HISTORY(
        selectedIcon = VidoraIcons.History,
        unselectedIcon = VidoraIcons.History,
        iconTextId = R.string.history,
        route = HistoryRoute,
    ),
    SETTINGS(
        selectedIcon = VidoraIcons.Settings,
        unselectedIcon = VidoraIcons.Settings,
        iconTextId = R.string.settings,
        route = SettingsRoute,
    ),
}
