import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.internal.extensions.stdlib.toDefaultLowerCase

plugins {
    kotlin("jvm")
    alias(libs.plugins.shadow)
    id("maven-publish")
}

group = "me.devadri.zenith"
version = property("version") as String

repositories {
    mavenCentral()
    maven("https://jitpack.io") {
        name = "JitPack"
    }
}

dependencies {
    api(libs.slf4j.provider)
    api(libs.jansi)
    api(libs.guava)
    api(libs.reflections)

    api(libs.kord)

    api(libs.adventure.api)
    api(libs.adventure.minimessage)
    api(libs.adventure.ansi)
}

kotlin {
    jvmToolchain(11)
}

tasks.named<ShadowJar>("shadowJar") {
    archiveClassifier.set("")
}

tasks.named("build") {
    finalizedBy(tasks.shadowJar)
}

sourceSets {
    create("exampleMain") {
        kotlin.srcDir("src/exampleMain/kotlin")
        resources.srcDir("src/exampleMain/resources")

        compileClasspath += sourceSets["main"].compileClasspath
        compileClasspath += sourceSets["main"].output

        runtimeClasspath += sourceSets["main"].runtimeClasspath
        runtimeClasspath += sourceSets["main"].output
    }
}

tasks.register<JavaExec>("runExample") {
    mainClass.set("me.devadri.zenith.example.MainKt")
    classpath = sourceSets["exampleMain"].runtimeClasspath
}

val versionIsBeta = (properties.get("version") as String).toDefaultLowerCase().contains("beta")

val sourcesImplementation = configurations.create("sourcesImplementation")

val unpackSources by tasks.registering(Sync::class) {
    doNotTrackState("Unpack dependency sources")

    from({
        sourcesImplementation.resolve().map { zipTree(it) }
    })
    into(layout.buildDirectory.dir("unpacked-dep-sources"))

    exclude("META-INF/**")
}

tasks.register("sourcesJar", Jar::class) {
    dependsOn(unpackSources)

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    from(sourceSets.main.get().allSource)
    from(layout.buildDirectory.dir("unpacked-dep-sources")) {
        exclude("META-INF/**")
    }
    archiveClassifier.set("sources")
}

if (project.hasProperty("NEXUS_USERNAME") && project.hasProperty("NEXUS_PASSWORD")) {

    publishing {
        repositories {
            maven {
                val baseUrl = "https://repo.devadri.es/repository/"

                url = uri(
                    baseUrl + if (versionIsBeta) "dev" else "releases"
                )
                credentials {
                    username = project.property("NEXUS_USERNAME") as String
                    password = project.property("NEXUS_PASSWORD") as String
                }
            }
        }
        publications {
            create<MavenPublication>("shadow") {
                groupId = rootProject.group as String
                artifactId = rootProject.name
                version = rootProject.version as String

                from(components["shadow"])
                artifact(tasks["sourcesJar"])
                pom {
                    name = rootProject.name
                    description.set(property("description") as String)
                    url = "https://github.com/Adrigamer2950/Zenith"

                    licenses {
                        license {
                            name = "GPL-3.0"
                            url = "https://www.gnu.org/licenses/gpl-3.0.html"
                        }
                    }

                    developers {
                        developer {
                            id = "Adrigamer2950"
                            name = "Adri"
                        }
                    }

                    scm {
                        url = "https://github.com/Adrigamer2950/Zenith"
                    }

                    issueManagement {
                        system = "GitHub"
                        url = "https://github.com/Adrigamer2950/Zenith/issues"
                    }
                }
            }
        }
    }
}
