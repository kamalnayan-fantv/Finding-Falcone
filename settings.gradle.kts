pluginManagement {
    repositories {
        google()
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

rootProject.name = "Finding The Falcon"
include(":app")
include(":core:data")
include(":core:domain")
include(":core:model")
include(":core:ui")
include(":core:commons")
