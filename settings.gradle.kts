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

rootProject.name = "FitRun"
include(":app")
include(":core")
include(":feature")
include(":build-logic")
include(":core:data")
include(":core:domain")
include(":core:design-system")
include(":core:common")
include(":core:datastore")
include(":core:ui")
include(":core:network")
