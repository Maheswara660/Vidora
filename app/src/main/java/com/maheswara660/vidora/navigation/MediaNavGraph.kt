package com.maheswara660.vidora.navigation

import android.content.Context
import android.content.Intent
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.maheswara660.vidora.feature.player.PlayerActivity
import com.maheswara660.vidora.feature.player.utils.PlayerApi
import com.maheswara660.vidora.feature.videopicker.navigation.HomeRoute
import com.maheswara660.vidora.feature.videopicker.navigation.MediaPickerRoute
import com.maheswara660.vidora.feature.videopicker.navigation.MediaPickerScreenType
import com.maheswara660.vidora.feature.videopicker.navigation.mediaPickerScreen
import com.maheswara660.vidora.feature.videopicker.navigation.navigateToMediaPickerScreen
import com.maheswara660.vidora.feature.videopicker.navigation.navigateToSearch
import com.maheswara660.vidora.feature.videopicker.navigation.searchScreen
import com.maheswara660.vidora.settings.navigation.navigateToSettings
import kotlinx.serialization.Serializable

@Serializable
data object MediaRootRoute

fun NavGraphBuilder.mediaNavGraph(
    context: Context,
    navController: NavHostController,
) {
    navigation<MediaRootRoute>(startDestination = HomeRoute) {
        mediaPickerScreen(
            onNavigateUp = navController::navigateUp,
            onPlayVideo = { uri ->
                val intent = Intent(context, PlayerActivity::class.java).apply {
                    action = Intent.ACTION_VIEW
                    data = uri
                }
                context.startActivity(intent)
            },
            onPlayVideos = { uris ->
                val intent = Intent(context, PlayerActivity::class.java).apply {
                    action = Intent.ACTION_VIEW
                    data = uris.first()
                    putParcelableArrayListExtra(PlayerApi.API_PLAYLIST, ArrayList(uris))
                }
                context.startActivity(intent)
            },
            onFolderClick = navController::navigateToMediaPickerScreen,
            onSettingsClick = navController::navigateToSettings,
            onSearchClick = navController::navigateToSearch,
        )

        searchScreen(
            onNavigateUp = navController::navigateUp,
            onPlayVideo = { uri ->
                val intent = Intent(context, PlayerActivity::class.java).apply {
                    action = Intent.ACTION_VIEW
                    data = uri
                }
                context.startActivity(intent)
            },
            onFolderClick = navController::navigateToMediaPickerScreen,
        )
    }
}
