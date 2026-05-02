# AndroidX & Kotlin rules
-keepattributes *Annotation*, Signature, InnerClasses, EnclosingMethod, SourceFile, LineNumberTable

# Kotlin Serialization
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepclassmembers class ** {
    @kotlinx.serialization.Serializable *;
}
-keepclassmembers class ** {
    @kotlinx.serialization.Serializer *;
}
-keep class * implements kotlinx.serialization.KSerializer
-keep class * implements kotlinx.serialization.SerializationStrategy
-keep class * implements kotlinx.serialization.DeserializationStrategy
-keep class kotlinx.serialization.json.** { *; }
-keepclassmembernames class kotlinx.serialization.internal.GeneratedSerializer { *; }

# Hilt / Dagger
-keep class dagger.hilt.** { *; }
-keep class com.maheswara660.vidora.hilt.** { *; }
-keep @dagger.hilt.android.lifecycle.HiltViewModel class * extends androidx.lifecycle.ViewModel
-keep class * extends dagger.hilt.internal.GeneratedComponent
-keep class * implements dagger.hilt.internal.GeneratedComponent
-keep class * extends dagger.hilt.internal.UnsafeCasts
-keep class hilt_aggregated_deps.** { *; }

# Room Database
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-keep @androidx.room.Dao interface *
-keep class * {
    @androidx.room.Database *;
    @androidx.room.Dao *;
    @androidx.room.Entity *;
    @androidx.room.TypeConverter *;
}
-keep class com.maheswara660.vidora.core.database.entities.** { *; }
-keep class com.maheswara660.vidora.core.database.relations.** { *; }

# Media3 / ExoPlayer
-keep class androidx.media3.** { *; }
-dontwarn androidx.media3.**
-keep class com.google.android.exoplayer2.** { *; }
-keep class * extends androidx.media3.common.util.HandlerWrapper
-keep @androidx.media3.common.util.UnstableApi class *
-keep @androidx.annotation.Keep class *

# Coil
-keep class coil.** { *; }
-keep class coil3.** { *; }
-dontwarn coil.**
-dontwarn coil3.**

# Vidora / NextLib (MediaInfo, etc.)
-keep class io.github.anilbeesetti.nextlib.** { *; }
-dontwarn io.github.anilbeesetti.nextlib.**
-keep class com.maheswara660.vidora.core.model.** { *; }
-keep class com.maheswara660.vidora.core.media.** { *; }
-keep class com.maheswara660.vidora.core.ui.components.** { *; }
-keep class com.maheswara660.vidora.settings.composables.** { *; }

# Accompanist
-keep class com.google.accompanist.** { *; }
-dontwarn com.google.accompanist.**

# JUniversalChardet
-keep class com.github.albfernandez.juniversalchardet.** { *; }

# General Kotlin rules
-keep class kotlin.Metadata { *; }
-keepclassmembers class ** {
    public void set*(***);
    public *** get*();
}

# Preserve line numbers for stack traces
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

# Navigation & Serialization
-keep @kotlinx.serialization.Serializable class * { *; }
-keepclassmembers class * {
    @kotlinx.serialization.Serializable *;
}
-keep class **$$serializer { *; }
-keepclassmembers class * {
    *** Companion;
}

# Hilt ViewModels & Activity
-keep class * extends androidx.activity.ComponentActivity { *; }
-keep class * extends androidx.lifecycle.ViewModel { *; }
-keep class com.maheswara660.vidora.MainViewModel { *; }
-keep class com.maheswara660.vidora.MainActivityUiState* { *; }

# Ensure NavHost routes aren't stripped
-keep class * implements androidx.navigation.NavArgs
-keep class * extends androidx.navigation.NavRoute

# Kotlin Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembernames class kotlinx.coroutines.android.HandlerContext {
    private long _h;
}

# DataStore
-keep class androidx.datastore.** { *; }

# AboutLibraries
-keep class com.mikepenz.aboutlibraries.** { *; }

# Reorderable
-keep class sh.calvin.reorderable.** { *; }

# OkHttp (indirectly used by Coil)
-keepattributes Signature, RuntimeVisibleAnnotations, AnnotationDefault
-keep class okhttp3.** { *; }
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**

# Compose & Material 3
-keep class androidx.compose.ui.platform.** { *; }
-keep class androidx.compose.runtime.** { *; }
-keep class androidx.compose.material3.** { *; }

# Hilt Generated Code
-keep class **_HiltModules* { *; }
-keep class **_HiltComponents* { *; }
-keep class **_Factory { *; }
-keep class **_MembersInjector { *; }
-keep class **_ViewModelModule { *; }

# Feature Screens (Navigation safety)
-keep class com.maheswara660.vidora.feature.**.screens.** { *; }
-keep class com.maheswara660.vidora.feature.**.ui.** { *; }
-keep class com.maheswara660.vidora.settings.screens.** { *; }
# Feature Screens & Services (Navigation & Component safety)
-keep class com.maheswara660.vidora.MainActivity { *; }
-keep class com.maheswara660.vidora.feature.**.service.** { *; }
-keep class com.maheswara660.vidora.feature.player.PlayerActivity { *; }

# Activity Result API
-keep class androidx.activity.result.** { *; }
