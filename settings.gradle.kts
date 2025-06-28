rootProject.name = "FitRun-android"

pluginManagement {
    includeBuild("build-logic")
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
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        maven { url = uri("https://devrepo.kakao.com/nexus/content/groups/public/") }
    }
}

// app
include(
    ":app"
)

// core
include(
    ":core:data",
    ":core:domain",
    ":core:design-system",
    ":core:common",
    ":core:datastore",
    ":core:ui",
    ":core:network",
)

// feature
include(
    ":feature:home",
    ":feature:login",
    ":feature:splash",
    ":feature:main",
    ":feature:navigator",
)