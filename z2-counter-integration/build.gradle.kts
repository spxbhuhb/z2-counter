import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/*
 * Copyright © 2020-2021, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
plugins {
    kotlin("multiplatform") version "1.9.0-dev-4392"
    id("hu.simplexion.z2.counter") version "0.1.0-SNAPSHOT"
    application
}

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/bootstrap")
}

kotlin {
    jvm {
        jvmToolchain(11)
    }
    js(IR) {
        browser()
        binaries.library()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("hu.simplexion.z2:z2-counter-runtime:0.1.0-SNAPSHOT")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        apiVersion = "2.0"
        languageVersion = "2.0"
    }
}