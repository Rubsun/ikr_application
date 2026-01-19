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

rootProject.name = "IKR Application"

include(":app")
include(":features:tasks:api")
include(":features:tasks:impl")
include(":libs:storage")
include(":libs:network")
include(":libs:imageloader")

