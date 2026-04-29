package com.maheswara660.vidora.feature.videopicker.screens.mediapicker

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.IconButton
import androidx.compose.material3.FloatingActionButtonMenu
import androidx.compose.material3.FloatingActionButtonMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.ToggleFloatingActionButton
import androidx.compose.material3.ToggleFloatingActionButtonDefaults.animateIcon
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.maheswara660.vidora.core.common.storagePermission
import com.maheswara660.vidora.core.media.services.MediaService
import com.maheswara660.vidora.core.model.ApplicationPreferences
import com.maheswara660.vidora.core.model.Folder
import com.maheswara660.vidora.core.model.MediaLayoutMode
import com.maheswara660.vidora.core.model.MediaViewMode
import com.maheswara660.vidora.core.model.Video
import com.maheswara660.vidora.core.ui.R
import com.maheswara660.vidora.core.ui.base.DataState
import com.maheswara660.vidora.core.ui.components.CancelButton
import com.maheswara660.vidora.core.ui.components.DoneButton
import com.maheswara660.vidora.core.ui.components.VidoraDialog
import com.maheswara660.vidora.core.ui.components.VidoraTopAppBar
import com.maheswara660.vidora.core.ui.composables.PermissionMissingView
import com.maheswara660.vidora.core.ui.designsystem.VidoraIcons
import com.maheswara660.vidora.core.ui.extensions.copy
import com.maheswara660.vidora.core.ui.preview.DayNightPreview
import com.maheswara660.vidora.core.ui.preview.VideoPickerPreviewParameterProvider
import com.maheswara660.vidora.core.ui.theme.VidoraTheme
import com.maheswara660.vidora.feature.videopicker.navigation.MediaPickerScreenType
import com.maheswara660.vidora.feature.videopicker.composables.CenterCircularProgressBar
import com.maheswara660.vidora.feature.videopicker.composables.MediaView
import com.maheswara660.vidora.feature.videopicker.composables.NoVideosFound
import com.maheswara660.vidora.feature.videopicker.composables.QuickSettingsDialog
import com.maheswara660.vidora.feature.videopicker.composables.RenameDialog
import com.maheswara660.vidora.feature.videopicker.composables.TextIconToggleButton
import com.maheswara660.vidora.feature.videopicker.composables.VideoInfoDialog
import com.maheswara660.vidora.feature.videopicker.state.SelectedFolder
import com.maheswara660.vidora.feature.videopicker.state.SelectedVideo
import com.maheswara660.vidora.feature.videopicker.state.rememberSelectionManager

@Composable
fun MediaPickerScreenRoute(
    screenType: MediaPickerScreenType,
    viewModel: MediaPickerViewModel = hiltViewModel(),
    onPlayVideo: (uri: Uri) -> Unit,
    onPlayVideos: (uris: List<Uri>) -> Unit,
    onFolderClick: (folderPath: String) -> Unit,
    onSettingsClick: () -> Unit,
    onSearchClick: () -> Unit,
    onNavigateUp: () -> Unit,
) {
    LaunchedEffect(screenType) {
        viewModel.onEvent(MediaPickerUiEvent.UpdateScreenType(screenType))
    }
    
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MediaPickerScreen(
        uiState = uiState,
        onPlayVideo = onPlayVideo,
        onPlayVideos = onPlayVideos,
        onNavigateUp = onNavigateUp,
        onFolderClick = onFolderClick,
        onSettingsClick = onSettingsClick,
        onSearchClick = onSearchClick,
        onEvent = viewModel::onEvent,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class, ExperimentalPermissionsApi::class)
@Composable
internal fun MediaPickerScreen(
    uiState: MediaPickerUiState,
    onNavigateUp: () -> Unit = {},
    onPlayVideo: (Uri) -> Unit = {},
    onPlayVideos: (List<Uri>) -> Unit = {},
    onFolderClick: (String) -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onEvent: (MediaPickerUiEvent) -> Unit = {},
) {
    val selectionManager = rememberSelectionManager()
    val permissionState = rememberPermissionState(permission = storagePermission)
    val lazyGridState = rememberLazyGridState()
    val selectVideoFileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { it?.let { onPlayVideo(it) } },
    )

    var isFabExpanded by rememberSaveable { mutableStateOf(false) }
    var showQuickSettingsDialog by rememberSaveable { mutableStateOf(false) }
    var isSearchActive by rememberSaveable { mutableStateOf(false) }

    var showRenameActionFor: Video? by rememberSaveable { mutableStateOf(null) }
    var showInfoActionFor: Video? by rememberSaveable { mutableStateOf(null) }
    var showDeleteVideosConfirmation by rememberSaveable { mutableStateOf(false) }

    val selectedItemsSize = selectionManager.selectedFolders.size + selectionManager.selectedVideos.size
    val totalItemsSize = (uiState.mediaDataState as? DataState.Success)?.value?.run { folderList.size + mediaList.size } ?: 0

    LaunchedEffect(uiState.screenType) {
        isSearchActive = false
    }

    Scaffold(
        topBar = {
            if (isSearchActive && uiState.screenType != MediaPickerScreenType.HOME) {
                VidoraTopAppBar(
                    title = {
                        OutlinedTextField(
                            value = uiState.searchQuery,
                            onValueChange = { onEvent(MediaPickerUiEvent.UpdateSearchQuery(it)) },
                            placeholder = { Text(stringResource(R.string.search)) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            shape = CircleShape,
                            singleLine = true,
                            leadingIcon = {
                                Icon(
                                    imageVector = VidoraIcons.Search,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                            },
                            trailingIcon = {
                                IconButton(onClick = { 
                                    isSearchActive = false
                                    onEvent(MediaPickerUiEvent.UpdateSearchQuery(""))
                                }) {
                                    Icon(
                                        imageVector = VidoraIcons.Close,
                                        contentDescription = null,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent,
                                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            )
                        )
                    },
                    navigationIcon = {},
                    actions = {}
                )
            } else {
                VidoraTopAppBar(
                    title = when {
                        selectionManager.isInSelectionMode -> ""
                        uiState.screenType == MediaPickerScreenType.VIDEOS -> stringResource(R.string.videos)
                        uiState.screenType == MediaPickerScreenType.HISTORY -> stringResource(R.string.history)
                        uiState.folderName != null -> uiState.folderName
                        else -> stringResource(R.string.app_name)
                    },
                    fontWeight = FontWeight.Bold.takeIf { uiState.folderName == null },
                    navigationIcon = {
                        if (selectionManager.isInSelectionMode) {
                            Row(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.secondaryContainer)
                                    .clickable { selectionManager.exitSelectionMode() }
                                    .padding(8.dp)
                                    .padding(end = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                Icon(
                                    imageVector = VidoraIcons.Close,
                                    contentDescription = stringResource(id = R.string.navigate_up),
                                )
                                Text(
                                    text = stringResource(R.string.m_n_selected, selectedItemsSize, totalItemsSize),
                                    style = MaterialTheme.typography.labelLarge,
                                )
                            }
                        } else if (uiState.folderName != null) {
                            IconButton(onClick = onNavigateUp) {
                                Icon(
                                    imageVector = VidoraIcons.ArrowBack,
                                    contentDescription = stringResource(id = R.string.navigate_up),
                                )
                            }
                        }
                    },
                    actions = {
                        if (selectionManager.isInSelectionMode) {
                            IconButton(
                                onClick = {
                                    if (selectedItemsSize != totalItemsSize) {
                                        (uiState.mediaDataState as? DataState.Success)?.value?.let { folder ->
                                            folder.folderList.forEach { selectionManager.selectFolder(it) }
                                            folder.mediaList.forEach { selectionManager.selectVideo(it) }
                                        }
                                    } else {
                                        selectionManager.clearSelection()
                                    }
                                },
                            ) {
                                Icon(
                                    imageVector = if (selectedItemsSize != totalItemsSize) {
                                        VidoraIcons.SelectAll
                                    } else {
                                        VidoraIcons.DeselectAll
                                    },
                                    contentDescription = if (selectedItemsSize != totalItemsSize) {
                                        stringResource(R.string.select_all)
                                    } else {
                                        stringResource(R.string.deselect_all)
                                    },
                                )
                            }
                        } else {
                            IconButton(onClick = {
                                if (uiState.screenType == MediaPickerScreenType.HOME) {
                                    onSearchClick()
                                } else {
                                    isSearchActive = true
                                }
                            }) {
                                Icon(
                                    imageVector = VidoraIcons.Search,
                                    contentDescription = stringResource(id = R.string.search),
                                )
                            }
                            IconButton(onClick = { showQuickSettingsDialog = true }) {
                                Icon(
                                    imageVector = VidoraIcons.DashBoard,
                                    contentDescription = stringResource(id = R.string.menu),
                                )
                            }
                        }
                    },
                )
            }
        },
        bottomBar = {
            SelectionActionsSheet(
                show = selectionManager.isInSelectionMode && selectionManager.allSelectedVideos.isNotEmpty(),
                showRenameAction = selectionManager.isSingleVideoSelected,
                showInfoAction = selectionManager.isSingleVideoSelected,
                onPlayAction = {
                    val videoUris = selectionManager.allSelectedVideos.map { it.uriString.toUri() }
                    onPlayVideos(videoUris)
                    selectionManager.clearSelection()
                },
                onRenameAction = {
                    val selectedVideo = selectionManager.selectedVideos.firstOrNull() ?: return@SelectionActionsSheet
                    val video = (uiState.mediaDataState as? DataState.Success)?.value?.mediaList
                        ?.find { it.uriString == selectedVideo.uriString } ?: return@SelectionActionsSheet
                    showRenameActionFor = video
                },
                onInfoAction = {
                    val selectedVideo = selectionManager.selectedVideos.firstOrNull() ?: return@SelectionActionsSheet
                    val video = (uiState.mediaDataState as? DataState.Success)?.value?.mediaList
                        ?.find { it.uriString == selectedVideo.uriString } ?: return@SelectionActionsSheet
                    showInfoActionFor = video
                    selectionManager.clearSelection()
                },
                onShareAction = {
                    onEvent(MediaPickerUiEvent.ShareVideos(selectionManager.allSelectedVideos.map { it.uriString }))
                },
                onDeleteAction = {
                    if (MediaService.willSystemAsksForDeleteConfirmation()) {
                        onEvent(MediaPickerUiEvent.DeleteVideos(selectionManager.allSelectedVideos.map { it.uriString }))
                        selectionManager.clearSelection()
                    } else {
                        showDeleteVideosConfirmation = true
                    }
                },
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
    ) { scaffoldPadding ->
        when (uiState.mediaDataState) {
            is DataState.Error -> {
            }

            is DataState.Loading -> {
                CenterCircularProgressBar(modifier = Modifier.padding(scaffoldPadding))
            }

            is DataState.Success -> {
                PullToRefreshBox(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = scaffoldPadding.calculateTopPadding())
                        .padding(start = scaffoldPadding.calculateStartPadding(LocalLayoutDirection.current))
                        .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                        .background(MaterialTheme.colorScheme.background),
                    isRefreshing = uiState.refreshing,
                    onRefresh = { onEvent(MediaPickerUiEvent.Refresh) },
                ) {
                    val updatedScaffoldPadding = scaffoldPadding.copy(top = 0.dp, start = 0.dp)
                    PermissionMissingView(
                        isGranted = permissionState.status.isGranted,
                        showRationale = permissionState.status.shouldShowRationale,
                        permission = permissionState.permission,
                        launchPermissionRequest = { permissionState.launchPermissionRequest() },
                    ) {
                        val rootFolder = uiState.mediaDataState.value
                        if (rootFolder == null || rootFolder.folderList.isEmpty() && rootFolder.mediaList.isEmpty()) {
                            NoVideosFound(contentPadding = updatedScaffoldPadding)
                            return@PermissionMissingView
                        }

                        MediaView(
                            rootFolder = rootFolder,
                            preferences = uiState.preferences,
                            onFolderClick = onFolderClick,
                            onVideoClick = { onPlayVideo(it) },
                            selectionManager = selectionManager,
                            lazyGridState = lazyGridState,
                            contentPadding = updatedScaffoldPadding,
                            onVideoLoaded = { onEvent(MediaPickerUiEvent.AddToSync(it)) },
                        )
                    }
                }
            }
        }
    }

    LaunchedEffect(lazyGridState.isScrollInProgress) {
        if (isFabExpanded && lazyGridState.isScrollInProgress) {
            isFabExpanded = false
        }
    }

    LaunchedEffect(selectionManager.isInSelectionMode) {
        if (selectionManager.isInSelectionMode) {
            isFabExpanded = false
        }
    }

    BackHandler(enabled = isSearchActive) {
        isSearchActive = false
        onEvent(MediaPickerUiEvent.UpdateSearchQuery(""))
    }

    BackHandler(enabled = isFabExpanded) {
        isFabExpanded = false
    }

    BackHandler(enabled = selectionManager.isInSelectionMode) {
        selectionManager.exitSelectionMode()
    }

    if (showQuickSettingsDialog) {
        QuickSettingsDialog(
            applicationPreferences = uiState.preferences,
            onDismiss = { showQuickSettingsDialog = false },
            updatePreferences = { onEvent(MediaPickerUiEvent.UpdateMenu(it)) },
        )
    }


    showRenameActionFor?.let { video ->
        RenameDialog(
            name = video.displayName,
            onDismiss = { showRenameActionFor = null },
            onDone = {
                onEvent(MediaPickerUiEvent.RenameVideo(video.uriString.toUri(), it))
                showRenameActionFor = null
                selectionManager.clearSelection()
            },
        )
    }

    showInfoActionFor?.let { video ->
        VideoInfoDialog(
            video = video,
            onDismiss = { showInfoActionFor = null },
        )
    }

    if (showDeleteVideosConfirmation) {
        DeleteConfirmationDialog(
            selectedVideos = selectionManager.selectedVideos,
            selectedFolders = selectionManager.selectedFolders,
            onConfirm = {
                onEvent(MediaPickerUiEvent.DeleteVideos(selectionManager.allSelectedVideos.map { it.uriString }))
                selectionManager.clearSelection()
                showDeleteVideosConfirmation = false
            },
            onCancel = { showDeleteVideosConfirmation = false },
        )
    }
}

@Composable
private fun DeleteConfirmationDialog(
    modifier: Modifier = Modifier,
    selectedVideos: Set<SelectedVideo>,
    selectedFolders: Set<SelectedFolder>,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
) {
    VidoraDialog(
        onDismissRequest = onCancel,
        title = {
            Text(
                text = when {
                    selectedVideos.isEmpty() -> when (selectedFolders.size) {
                        1 -> stringResource(R.string.delete_one_folder)
                        else -> stringResource(R.string.delete_folders, selectedFolders.size)
                    }

                    selectedFolders.isEmpty() -> when (selectedVideos.size) {
                        1 -> stringResource(R.string.delete_one_video)
                        else -> stringResource(R.string.delete_videos, selectedVideos.size)
                    }

                    else -> stringResource(R.string.delete_items, selectedFolders.size + selectedVideos.size)
                },
                modifier = Modifier.fillMaxWidth(),
            )
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                modifier = modifier,
            ) {
                Text(text = stringResource(R.string.delete))
            }
        },
        dismissButton = { CancelButton(onClick = onCancel) },
        modifier = modifier,
        content = {
            Text(
                text = if ((selectedFolders.size + selectedVideos.size) == 1) {
                    stringResource(R.string.delete_item_info)
                } else {
                    stringResource(R.string.delete_items_info)
                },
                style = MaterialTheme.typography.titleSmall,
            )
        },
    )
}


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun SelectionActionsSheet(
    modifier: Modifier = Modifier,
    show: Boolean,
    showRenameAction: Boolean,
    showInfoAction: Boolean,
    onPlayAction: () -> Unit,
    onRenameAction: () -> Unit,
    onShareAction: () -> Unit,
    onInfoAction: () -> Unit,
    onDeleteAction: () -> Unit,
) {
    AnimatedVisibility(
        modifier = modifier.padding(
            start = WindowInsets.displayCutout.asPaddingValues()
                .calculateStartPadding(LocalLayoutDirection.current),
        ),
        visible = show,
        enter = slideInVertically { it },
        exit = slideOutVertically { it },
    ) {
        val shape = MaterialTheme.shapes.largeIncreased.copy(
            bottomStart = ZeroCornerSize,
            bottomEnd = ZeroCornerSize,
        )
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = shape,
                    )
                    .clip(shape)
                    .horizontalScroll(rememberScrollState())
                    .navigationBarsPadding()
                    .padding(
                        horizontal = 8.dp,
                        vertical = 12.dp,
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                SelectionAction(
                    imageVector = VidoraIcons.Play,
                    title = stringResource(R.string.play),
                    onClick = onPlayAction,
                )
                if (showRenameAction) {
                    SelectionAction(
                        imageVector = VidoraIcons.Edit,
                        title = stringResource(R.string.rename),
                        onClick = onRenameAction,
                    )
                }
                SelectionAction(
                    imageVector = VidoraIcons.Share,
                    title = stringResource(R.string.share),
                    onClick = onShareAction,
                )
                if (showInfoAction) {
                    SelectionAction(
                        imageVector = VidoraIcons.Info,
                        title = stringResource(id = R.string.info),
                        onClick = onInfoAction,
                    )
                }
                SelectionAction(
                    imageVector = VidoraIcons.Delete,
                    title = stringResource(id = R.string.delete),
                    onClick = onDeleteAction,
                )
            }
        }
    }
}

@Composable
private fun SelectionAction(
    imageVector: ImageVector,
    title: String,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .defaultMinSize(
                minWidth = 75.dp,
                minHeight = 64.dp,
            )
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp,
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = title,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = title,
            modifier = Modifier,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
    }
}

@PreviewScreenSizes
@PreviewLightDark
@Composable
private fun MediaPickerScreenPreview(
    @PreviewParameter(VideoPickerPreviewParameterProvider::class)
    videos: List<Video>,
) {
    VidoraTheme {
        MediaPickerScreen(
            uiState = MediaPickerUiState(
                folderName = null,
                mediaDataState = DataState.Success(
                    value = Folder(
                        name = "Root Folder",
                        path = "/root",
                        dateModified = System.currentTimeMillis(),
                        folderList = listOf(
                            Folder(name = "Folder 1", path = "/root/folder1", dateModified = System.currentTimeMillis()),
                            Folder(name = "Folder 2", path = "/root/folder2", dateModified = System.currentTimeMillis()),
                        ),
                        mediaList = videos,
                    ),
                ),
                preferences = ApplicationPreferences().copy(
                    mediaViewMode = MediaViewMode.FOLDER_TREE,
                    mediaLayoutMode = MediaLayoutMode.GRID,
                ),
            ),
        )
    }
}

@Preview
@Composable
private fun ButtonPreview() {
    Surface {
        TextIconToggleButton(
            text = "Title",
            icon = VidoraIcons.Title,
            onClick = {},
        )
    }
}

@DayNightPreview
@Composable
private fun MediaPickerNoVideosFoundPreview() {
    VidoraTheme {
        Surface {
            MediaPickerScreen(
                uiState = MediaPickerUiState(
                    folderName = null,
                    mediaDataState = DataState.Success(null),
                    preferences = ApplicationPreferences(),
                ),
            )
        }
    }
}

@DayNightPreview
@Composable
private fun MediaPickerLoadingPreview() {
    VidoraTheme {
        Surface {
            MediaPickerScreen(
                uiState = MediaPickerUiState(
                    folderName = null,
                    mediaDataState = DataState.Loading,
                    preferences = ApplicationPreferences(),
                ),
            )
        }
    }
}
