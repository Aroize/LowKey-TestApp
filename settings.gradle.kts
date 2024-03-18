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

rootProject.name = "Pexels"
include(":app")
include(":feed")
include(":core")
include(":core:di")
include(":core:navigation")
include(":core:network")
include(":core:coroutines")
include(":core:utils")
include(":core:recycler")
include(":core:arch")
include(":details")
include(":details:api")
include(":details:impl")
include(":posts-domain")
include(":core:db")
