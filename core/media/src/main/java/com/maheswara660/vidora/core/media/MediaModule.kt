package com.maheswara660.vidora.core.media

import android.content.Context
import coil3.ImageLoader
import coil3.disk.DiskCache
import coil3.disk.directory
import coil3.request.CachePolicy
import coil3.request.crossfade
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.maheswara660.vidora.core.media.services.LocalMediaService
import com.maheswara660.vidora.core.media.services.MediaService
import com.maheswara660.vidora.core.media.sync.LocalMediaInfoSynchronizer
import com.maheswara660.vidora.core.media.sync.LocalMediaSynchronizer
import com.maheswara660.vidora.core.media.sync.MediaInfoSynchronizer
import com.maheswara660.vidora.core.media.sync.MediaSynchronizer
import com.maheswara660.vidora.core.model.ApplicationPreferences
import com.maheswara660.vidora.core.model.ThumbnailGenerationStrategy
import javax.inject.Singleton
import okio.FileSystem

@Module
@InstallIn(SingletonComponent::class)
interface MediaModule {

    @Binds
    @Singleton
    fun bindsMediaSynchronizer(
        mediaSynchronizer: LocalMediaSynchronizer,
    ): MediaSynchronizer

    @Binds
    @Singleton
    fun bindsMediaInfoSynchronizer(
        mediaInfoSynchronizer: LocalMediaInfoSynchronizer,
    ): MediaInfoSynchronizer

    @Binds
    @Singleton
    fun bindMediaService(
        mediaService: LocalMediaService,
    ): MediaService
}
