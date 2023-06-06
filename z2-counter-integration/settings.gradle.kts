/*
 * Copyright © 2020-2021, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
rootProject.name = "z2-counter-integration"

pluginManagement {
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "hu.simplexion.z2.counter") {
                useModule("hu.simplexion.z2:z2-counter-gradle-plugin:0.1.0-SNAPSHOT")
            }
        }
    }
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/bootstrap")
    }
}