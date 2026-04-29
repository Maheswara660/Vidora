plugins {
    alias(libs.plugins.aboutLibraries) apply false
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.kotlinJvm) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.ktlint) apply false
}

subprojects {
    apply(plugin = rootProject.libs.plugins.ktlint.get().pluginId)
    // apply(plugin = rootProject.libs.plugins.aboutLibraries.get().pluginId)

    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        android.set(true)
        outputColorName.set("RED")
        ignoreFailures.set(true)
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        compilerOptions {
            freeCompilerArgs.add("-Xannotation-default-target=param-property")
        }
    }
}