package com.maheswara660.vidora

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import dagger.hilt.android.HiltAndroidApp
import com.maheswara660.vidora.core.common.di.ApplicationScope
import com.maheswara660.vidora.core.data.repository.PreferencesRepository
import com.maheswara660.vidora.crash.CrashActivity
import com.maheswara660.vidora.crash.GlobalExceptionHandler
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope

@HiltAndroidApp
class VidoraApplication : Application(), SingletonImageLoader.Factory {

    @Inject
    lateinit var preferencesRepository: PreferencesRepository

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    @ApplicationScope
    lateinit var applicationScope: CoroutineScope

    override fun onCreate() {
        super.onCreate()
        Thread.setDefaultUncaughtExceptionHandler(GlobalExceptionHandler(applicationContext, CrashActivity::class.java))
    }

    override fun newImageLoader(context: PlatformContext): ImageLoader = imageLoader
}
