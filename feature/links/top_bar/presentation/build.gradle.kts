plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    androidTarget()

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "TopBarPresentation"
            isStatic = true
        }
    }

    jvm()

    sourceSets {
        commonMain.dependencies {
            // Compose
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            // Koin (feature DI)
            implementation(project.dependencies.platform(libs.koinBom))
            implementation(libs.koinCore)
            implementation(libs.koinComposeViewModel)

            // Navigation
            implementation(libs.navigation.compose)

            // DateTime
            implementation(libs.kotlinx.datetime)

            // Core
            implementation(projects.core.model.routes)
            implementation(projects.core.utils)

            // UI
            implementation(projects.ui.theme)
            implementation(projects.ui.components.iconButton)
            implementation(projects.ui.utils)

            // Features
            implementation(projects.feature.links.topBar.domain)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

dependencies {
    // Compose Preview tooling only for debug builds
    debugImplementation(libs.uiTooling)
}

android {
    namespace = "com.pierre.shortner.feature.links.top_bar.presentation"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        consumerProguardFiles("consumer-rules.pro")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}
