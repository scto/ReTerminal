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
        maven { url = uri("https://jitpack.io")}
        maven {
            url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
        }
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
        maven {
            url = uri("https://s01.oss.sonatype.org/content/repositories/releases/")
            content {
                includeGroupByRegex("dev.rikka.*")
                includeGroup("org.lsposed.hiddenapibypass")
            }
        }
     
    }
    plugins {
        kotlin("jvm") version "2.1.10"
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io")}
        maven {
            url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
        }
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
        maven {
            url = uri("https://s01.oss.sonatype.org/content/repositories/releases/")
            content {
                includeGroupByRegex("dev.rikka.*")
                includeGroup("org.lsposed.hiddenapibypass")
            }
        }
       
    }
}

rootProject.name = "Xed-Editor"
include(":app")
include(":core:main")

include(":core:components")
include(":core:resources")
include(":core:rish")
