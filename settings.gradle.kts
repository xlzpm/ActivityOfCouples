pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Training"
include(":app")
include(":network")
include(":localdb")
include(":localdb:randomActivities")
include(":firebase")
include(":shared")
include(":shared:components")
include(":firebase_auth:auth")
include(":firebase:auth")
include(":firebase:firestore")
include(":mvi")
