package com.maheswara660.vidora.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.maheswara660.vidora.settings.Setting
import com.maheswara660.vidora.settings.navigation.aboutPreferencesScreen
import com.maheswara660.vidora.settings.navigation.appearancePreferencesScreen
import com.maheswara660.vidora.settings.navigation.audioPreferencesScreen
import com.maheswara660.vidora.settings.navigation.decoderPreferencesScreen
import com.maheswara660.vidora.settings.navigation.folderPreferencesScreen
import com.maheswara660.vidora.settings.navigation.generalPreferencesScreen
import com.maheswara660.vidora.settings.navigation.librariesScreen
import com.maheswara660.vidora.settings.navigation.mediaLibraryPreferencesScreen
import com.maheswara660.vidora.settings.navigation.navigateToAboutPreferences
import com.maheswara660.vidora.settings.navigation.navigateToAppearancePreferences
import com.maheswara660.vidora.settings.navigation.gesturePreferencesScreen
import com.maheswara660.vidora.settings.navigation.navigateToAudioPreferences
import com.maheswara660.vidora.settings.navigation.navigateToDecoderPreferences
import com.maheswara660.vidora.settings.navigation.navigateToGesturePreferences
import com.maheswara660.vidora.settings.navigation.navigateToFolderPreferencesScreen
import com.maheswara660.vidora.settings.navigation.navigateToGeneralPreferences
import com.maheswara660.vidora.settings.navigation.navigateToLibraries
import com.maheswara660.vidora.settings.navigation.navigateToMediaLibraryPreferencesScreen
import com.maheswara660.vidora.settings.navigation.navigateToPlayerPreferences
import com.maheswara660.vidora.settings.navigation.navigateToSubtitlePreferences
import com.maheswara660.vidora.settings.navigation.navigateToThumbnailPreferencesScreen
import com.maheswara660.vidora.settings.navigation.playerPreferencesScreen
import com.maheswara660.vidora.settings.navigation.SettingsRoute
import com.maheswara660.vidora.settings.navigation.settingsScreen
import kotlinx.serialization.Serializable
import com.maheswara660.vidora.settings.navigation.subtitlePreferencesScreen
import com.maheswara660.vidora.settings.navigation.thumbnailPreferencesScreen

@Serializable
data object SettingsRootRoute

fun NavGraphBuilder.settingsNavGraph(
    navController: NavHostController,
) {
    navigation<SettingsRootRoute>(
        startDestination = SettingsRoute,
    ) {
        settingsScreen(
            onNavigateUp = navController::navigateUp,
            onItemClick = { setting ->
                when (setting) {
                    Setting.APPEARANCE -> navController.navigateToAppearancePreferences()
                    Setting.MEDIA_LIBRARY -> navController.navigateToMediaLibraryPreferencesScreen()
                    Setting.PLAYER -> navController.navigateToPlayerPreferences()
                    Setting.GESTURES -> navController.navigateToGesturePreferences()
                    Setting.DECODER -> navController.navigateToDecoderPreferences()
                    Setting.AUDIO -> navController.navigateToAudioPreferences()
                    Setting.SUBTITLE -> navController.navigateToSubtitlePreferences()
                    Setting.GENERAL -> navController.navigateToGeneralPreferences()
                    Setting.ABOUT -> navController.navigateToAboutPreferences()
                }
            },
        )
        appearancePreferencesScreen(
            onNavigateUp = navController::navigateUp,
        )
        mediaLibraryPreferencesScreen(
            onNavigateUp = navController::navigateUp,
            onFolderSettingClick = navController::navigateToFolderPreferencesScreen,
            onThumbnailSettingClick = navController::navigateToThumbnailPreferencesScreen,
        )
        thumbnailPreferencesScreen(
            onNavigateUp = navController::navigateUp,
        )
        folderPreferencesScreen(
            onNavigateUp = navController::navigateUp,
        )
        playerPreferencesScreen(
            onNavigateUp = navController::navigateUp,
        )
        gesturePreferencesScreen(
            onNavigateUp = navController::navigateUp,
        )
        decoderPreferencesScreen(
            onNavigateUp = navController::navigateUp,
        )
        audioPreferencesScreen(
            onNavigateUp = navController::navigateUp,
        )
        subtitlePreferencesScreen(
            onNavigateUp = navController::navigateUp,
        )
        generalPreferencesScreen(
            onNavigateUp = navController::navigateUp,
        )
        aboutPreferencesScreen(
            onLibrariesClick = navController::navigateToLibraries,
            onNavigateUp = navController::navigateUp,
        )
        librariesScreen(
            onNavigateUp = navController::navigateUp,
        )
    }
}
