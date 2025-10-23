rootProject.name = "Shortener"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

include(":composeApp")

// Core
include(":core:network")
include(":core:model:data_status")
include(":core:model:theme")
include(":core:model:routes")
include(":core:provider:data_store")
include(":core:provider:koin")
include(":core:provider:room")
include(":core:utils")

// UI
include(":ui:components:icon_button")
include(":ui:components:shimmer")
include(":ui:components:spacer")
include(":ui:navigation")
include(":ui:theme")
include(":ui:utils")

// Feature
include(":feature:material_you:data")
include(":feature:material_you:domain")
include(":feature:material_you:presentation")
include(":feature:links:data")
include(":feature:links:domain")
include(":feature:links:presentation")
include(":feature:theme_selection:data")
include(":feature:theme_selection:domain")
include(":feature:theme_selection:presentation")
