package com.maheswara660.vidora.settings.screens.about

import android.content.ClipData
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Code
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.core.net.toUri
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maheswara660.vidora.core.common.extensions.appIcon
import com.maheswara660.vidora.core.ui.R
import com.maheswara660.vidora.core.ui.components.ClickablePreferenceItem
import com.maheswara660.vidora.core.ui.components.ListSectionTitle
import com.maheswara660.vidora.core.ui.components.VidoraTopAppBar
import com.maheswara660.vidora.core.ui.designsystem.VidoraIcons
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AboutPreferencesScreen(
    onLibrariesClick: () -> Unit,
    onNavigateUp: () -> Unit,
) {
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current
    val clipboard = LocalClipboard.current
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            VidoraTopAppBar(
                title = stringResource(id = R.string.about_name),
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = VidoraIcons.ArrowBack,
                            contentDescription = stringResource(id = R.string.navigate_up),
                        )
                    }
                },
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
        ) {
            AboutApp()
            
            Spacer(modifier = Modifier.height(32.dp))

            DeveloperCard()

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.connect_and_support),
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            AboutActionItem(
                icon = Icons.Rounded.Code,
                title = stringResource(R.string.github_repository),
                description = stringResource(R.string.github_repository_desc),
                onClick = {
                    uriHandler.openUriOrShowToast("https://github.com/maheswara660/Vidora", context)
                }
            )

            AboutActionItem(
                icon = Icons.Rounded.Favorite,
                title = stringResource(R.string.support_development),
                description = stringResource(R.string.support_development_desc),
                onClick = {
                    uriHandler.openUriOrShowToast("https://ko-fi.com/maheswara660", context)
                }
            )

            AboutActionItem(
                icon = Icons.Rounded.Language,
                title = stringResource(R.string.open_source_license),
                description = stringResource(R.string.open_source_license_desc),
                onClick = {
                    uriHandler.openUriOrShowToast("https://github.com/maheswara660/Vidora/blob/main/LICENSE", context)
                }
            )

            AboutActionItem(
                icon = VidoraIcons.Settings,
                title = stringResource(R.string.libraries),
                description = stringResource(R.string.libraries_desc),
                onClick = onLibrariesClick
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "© 2026 Maheswara660\nMade with ❤️ for the community",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun AboutApp(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val appVersion = remember { context.appVersion() }
    val appIcon = remember { context.appIcon()?.asImageBitmap() }

    val colorPrimary = MaterialTheme.colorScheme.primaryContainer
    val colorTertiary = MaterialTheme.colorScheme.tertiaryContainer

    val transition = rememberInfiniteTransition()
    val fraction by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 5000),
            repeatMode = RepeatMode.Reverse,
        ),
    )
    val cornerRadius = 24.dp

    Column(
        modifier = modifier
            .padding(
                vertical = 32.dp,
                horizontal = 8.dp,
            )
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (appIcon != null) {
            Image(
                bitmap = appIcon,
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(22.dp)),
            )
        } else {
            Image(
                painter = painterResource(id = context.applicationInfo.icon),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(22.dp)),
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Text(
            text = "Version $appVersion",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
fun DeveloperCard() {
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                uriHandler.openUriOrShowToast("https://github.com/maheswara660", context)
            },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(MaterialTheme.colorScheme.primary, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.Person,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(32.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column {
                Text(
                    text = stringResource(R.string.developed_by),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(R.string.dev_bio),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}

@Composable
fun AboutActionItem(
    icon: ImageVector,
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

private fun Context.appVersion(): String {
    val packageInfo = packageManager.getPackageInfo(packageName, 0)

    @Suppress("DEPRECATION")
    val versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        packageInfo.longVersionCode
    } else {
        packageInfo.versionCode
    }

    return packageInfo.versionName?.substringBefore(" ") ?: "1.0.0"
}

internal fun UriHandler.openUriOrShowToast(uri: String, context: Context) {
    try {
        openUri(uri = uri)
    } catch (e: Exception) {
        Toast.makeText(context, context.getString(R.string.error_opening_link), Toast.LENGTH_SHORT).show()
    }
}
