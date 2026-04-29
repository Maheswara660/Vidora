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
-dontwarn coil.**

# Vidora / NextLib (MediaInfo, etc.)
-keep class io.github.anilbeesetti.nextlib.** { *; }
-dontwarn io.github.anilbeesetti.nextlib.**
-keep class com.maheswara660.vidora.core.model.** { *; }
-keep class com.maheswara660.vidora.core.media.** { *; }

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