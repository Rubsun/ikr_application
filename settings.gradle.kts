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
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "ikr_application"
include(":app")
include(":features:dyatlova:api")
include(":features:dyatlova:impl")
include(":features:nastyazz:api")
include(":features:nastyazz:impel")
include(":features:nfirex:api")
include(":features:nfirex:impl")
include(":libs:injector")
include(":features:grigoran:api")
include(":features:grigoran:impl")
include(":features:rin2396:api")
include(":features:rin2396:impl")
include(":features:stupishin:api")
include(":features:stupishin:impl")
include(":features:n0tsszzz:api")
include(":features:n0tsszzz:impl")
include(":features:artemkaa:api")
include(":features:artemkaa:impl")
include(":features:antohaot:api")
include(":features:antohaot:impl")
include(":features:drain678:api")
include(":features:drain678:impl")
include(":libs:primitivestorage:api")
include(":libs:primitivestorage:data")
include(":features:denisova:api")
include(":features:denisova:impl")
include(":features:zagora:api")
include(":features:zagora:impl")
include(":features:MomusWinner:api")
include(":features:MomusWinner:impl")
include(":features:egorik4:api")
include(":features:egorik4:impl")
include(":features:alexcode69:api")
include(":features:alexcode69:impl")
include(":features:rubsun:api")
include(":features:rubsun:impl")