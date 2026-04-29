package com.maheswara660.vidora.feature.videopicker.navigation

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.maheswara660.vidora.feature.videopicker.screens.mediapicker.MediaPickerScreenRoute
import kotlinx.serialization.Serializable

internal const val folderIdArg = "folderId"

internal class FolderArgs(val folderId: String?) {
    constructor(savedStateHandle: SavedStateHandle) :
        this(savedStateHandle.get<String>(folderIdArg)?.let { Uri.decode(it) })
}

@Serializable
data class MediaPickerRoute(
    val folderId: String? = null,
)

@Serializable
enum class MediaPickerScreenType {
    HOME,
    VIDEOS,
    HISTORY,
}

@Serializable
data object HomeRoute

@Serializable
data object VideosRoute

@Serializable
data object HistoryRoute



fun NavController.navigateToMediaPickerScreen(
    folderId: String,
    navOptions: NavOptions? = null,
) {
    val encodedFolderId = Uri.encode(folderId)
    this.navigate(MediaPickerRoute(encodedFolderId), navOptions)
}

fun NavGraphBuilder.mediaPickerScreen(
    onNavigateUp: () -> Unit,
    onPlayVideo: (uri: Uri) -> Unit,
    onPlayVideos: (uris: List<Uri>) -> Unit,
    onFolderClick: (folderPath: String) -> Unit,
    onSettingsClick: () -> Unit,
    onSearchClick: () -> Unit,
) {
    composable<HomeRoute> {
        MediaPickerScreenRoute(
            screenType = MediaPickerScreenType.HOME,
            onPlayVideo = onPlayVideo,
            onPlayVideos = onPlayVideos,
            onNavigateUp = onNavigateUp,
            onFolderClick = onFolderClick,
            onSettingsClick = onSettingsClick,
            onSearchClick = onSearchClick,
        )
    }
    composable<VideosRoute> {
        MediaPickerScreenRoute(
            screenType = MediaPickerScreenType.VIDEOS,
            onPlayVideo = onPlayVideo,
            onPlayVideos = onPlayVideos,
            onNavigateUp = onNavigateUp,
            onFolderClick = onFolderClick,
            onSettingsClick = onSettingsClick,
            onSearchClick = onSearchClick,
        )
    }
    composable<HistoryRoute> {
        MediaPickerScreenRoute(
            screenType = MediaPickerScreenType.HISTORY,
            onPlayVideo = onPlayVideo,
            onPlayVideos = onPlayVideos,
            onNavigateUp = onNavigateUp,
            onFolderClick = onFolderClick,
            onSettingsClick = onSettingsClick,
            onSearchClick = onSearchClick,
        )
    }
    composable<MediaPickerRoute> {
        MediaPickerScreenRoute(
            screenType = MediaPickerScreenType.HOME,
            onPlayVideo = onPlayVideo,
            onPlayVideos = onPlayVideos,
            onNavigateUp = onNavigateUp,
            onFolderClick = onFolderClick,
            onSettingsClick = onSettingsClick,
            onSearchClick = onSearchClick,
        )
    }
}
