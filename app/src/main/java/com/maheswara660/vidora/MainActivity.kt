package com.maheswara660.vidora

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import dagger.hilt.android.AndroidEntryPoint
import com.maheswara660.vidora.core.common.storagePermission
import com.maheswara660.vidora.core.media.services.MediaService
import com.maheswara660.vidora.core.media.sync.MediaSynchronizer
import com.maheswara660.vidora.core.model.ThemeConfig
import com.maheswara660.vidora.core.ui.theme.VidoraTheme
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.toRoute
import com.maheswara660.vidora.feature.videopicker.navigation.MediaPickerRoute
import com.maheswara660.vidora.settings.navigation.SettingsRoute
import com.maheswara660.vidora.navigation.MediaRootRoute
import com.maheswara660.vidora.navigation.TopLevelDestination
import com.maheswara660.vidora.navigation.components.VidoraBottomBar
import com.maheswara660.vidora.feature.videopicker.navigation.HomeRoute
import com.maheswara660.vidora.feature.videopicker.navigation.VideosRoute
import com.maheswara660.vidora.feature.videopicker.navigation.HistoryRoute
import com.maheswara660.vidora.navigation.components.VidoraBottomBar
import com.maheswara660.vidora.navigation.mediaNavGraph
import com.maheswara660.vidora.navigation.settingsNavGraph
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import javax.inject.Inject
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var synchronizer: MediaSynchronizer

    @Inject
    lateinit var mediaService: MediaService

    private val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mediaService.initialize(this@MainActivity)

        var uiState: MainActivityUiState by mutableStateOf(MainActivityUiState.Loading)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    uiState = state
                }
            }
        }

        setContent {
            val shouldUseDarkTheme = shouldUseDarkTheme(uiState = uiState)

            LaunchedEffect(shouldUseDarkTheme) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        lightScrim = Color.TRANSPARENT,
                        darkScrim = Color.TRANSPARENT,
                        detectDarkMode = { shouldUseDarkTheme },
                    ),
                    navigationBarStyle = SystemBarStyle.auto(
                        lightScrim = Color.TRANSPARENT,
                        darkScrim = Color.TRANSPARENT,
                        detectDarkMode = { shouldUseDarkTheme },
                    ),
                )
            }

            VidoraTheme(
                darkTheme = shouldUseDarkTheme,
                themeConfig = getThemeConfig(uiState = uiState),
                accentColorIndex = getAccentColorIndex(uiState = uiState),
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface,
                ) {
                    val storagePermissionState = rememberPermissionState(permission = storagePermission)

                    LifecycleEventEffect(event = Lifecycle.Event.ON_START) {
                        storagePermissionState.launchPermissionRequest()
                    }

                    LaunchedEffect(key1 = storagePermissionState.status.isGranted) {
                        if (storagePermissionState.status.isGranted) {
                            synchronizer.startSync()
                        }
                    }

                    val mainNavController = rememberNavController()
                    val navBackStackEntry by mainNavController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    val topLevelDestinations = TopLevelDestination.entries

                    Scaffold(
                        bottomBar = {
                            val isTopLevelDestination = currentDestination?.let { dest ->
                                dest.hasRoute<HomeRoute>() ||
                                dest.hasRoute<VideosRoute>() ||
                                dest.hasRoute<HistoryRoute>() ||
                                dest.hasRoute<SettingsRoute>()
                            } ?: false

                            if (isTopLevelDestination) {
                                VidoraBottomBar(
                                    destinations = topLevelDestinations,
                                    onNavigateToDestination = { destination ->
                                        mainNavController.navigate(destination.route) {
                                            popUpTo(mainNavController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    navBackStackEntry = navBackStackEntry,
                                )
                            }
                        },
                    ) { innerPadding ->
                        NavHost(
                            navController = mainNavController,
                            startDestination = MediaRootRoute,
                            modifier = Modifier.padding(innerPadding),
                            enterTransition = { fadeIn(animationSpec = tween(300)) },
                            exitTransition = { fadeOut(animationSpec = tween(300)) },
                            popEnterTransition = { fadeIn(animationSpec = tween(300)) },
                            popExitTransition = { fadeOut(animationSpec = tween(300)) },
                        ) {
                            mediaNavGraph(
                                context = this@MainActivity,
                                navController = mainNavController,
                            )
                            settingsNavGraph(navController = mainNavController)
                        }
                    }
                }
            }
        }
    }
}

/**
 * Returns `true` if dark theme should be used, as a function of the [uiState] and the
 * current system context.
 */
@Composable
fun shouldUseDarkTheme(
    uiState: MainActivityUiState,
): Boolean = when (uiState) {
    MainActivityUiState.Loading -> isSystemInDarkTheme()
    is MainActivityUiState.Success -> when (uiState.preferences.themeConfig) {
        ThemeConfig.SYSTEM -> isSystemInDarkTheme()
        ThemeConfig.OFF -> false
        ThemeConfig.ON, ThemeConfig.AMOLED -> true
    }
}

@Composable
fun getThemeConfig(
    uiState: MainActivityUiState,
): ThemeConfig = when (uiState) {
    MainActivityUiState.Loading -> ThemeConfig.SYSTEM
    is MainActivityUiState.Success -> uiState.preferences.themeConfig
}

@Composable
fun getAccentColorIndex(
    uiState: MainActivityUiState,
): Int = when (uiState) {
    MainActivityUiState.Loading -> -1
    is MainActivityUiState.Success -> uiState.preferences.accentColorIndex
}
