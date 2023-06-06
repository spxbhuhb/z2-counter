/*
 * Copyright © 2020-2023, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
plugins {
    kotlin("multiplatform") version "1.9.0-dev-4392"
    signing
    `maven-publish`
}

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/bootstrap")
}

group = "hu.simplexion.z2"
version = "0.1.0"

kotlin {

    jvm {
        jvmToolchain(11)
    }

    js(IR) {
        browser()
        nodejs()
        binaries.library()
    }

}

// ----  Publishing ----

val String.propValue
    get() = (System.getenv(this.toUpperCase().replace('.', '_')) ?: project.findProperty(this))?.toString() ?: ""

val isPublishing = "z2.publish".propValue
val publishSnapshotUrl = "z2.publish.snapshot.url".propValue
val publishReleaseUrl = "z2.publish.release.url".propValue
val publishUsername = "z2.publish.username".propValue
val publishPassword = "z2.publish.password".propValue
val isSnapshot = "SNAPSHOT" in project.version.toString()

val baseName = "z2-counter-runtime"
val pomName = "Z2 Counter Runtime"
val scmPath = "spxbhuhb/z2-counter"

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

signing {
    if (project.properties["signing.keyId"] == null) {
        useGpgCmd()
    }
    sign(publishing.publications)
}

publishing {

    repositories {
        maven {
            name = "MavenCentral"
            url = project.uri(requireNotNull(if (isSnapshot) publishSnapshotUrl else publishReleaseUrl))
            credentials {
                username = publishUsername
                password = publishPassword
            }
        }
    }

    publications.withType<MavenPublication>().all {

        artifact(javadocJar.get())

        pom {
            description.set("Runtime of Counter, a Kotlin compiler plugin that adds code that counts how many times some code runs.")
            name.set(pomName)
            url.set("https://github.com/$scmPath")
            scm {
                url.set("https://github.com/$scmPath")
                connection.set("scm:git:git://github.com/$scmPath.git")
                developerConnection.set("scm:git:ssh://git@github.com/$scmPath.git")
            }
            licenses {
                license {
                    name.set("Apache 2.0")
                    url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    distribution.set("repo")
                }
            }
            developers {
                developer {
                    id.set("toth-istvan-zoltan")
                    name.set("Tóth István Zoltán")
                    url.set("https://github.com/toth-istvan-zoltan")
                    organization.set("Simplexion Kft.")
                    organizationUrl.set("https://www.simplexion.hu")
                }
            }
        }
    }
}