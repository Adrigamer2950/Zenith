import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm")
    alias(libs.plugins.shadow)
}

group = "me.devadri.zenith"
version = property("version") as String

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.slf4j.provider)
    implementation(libs.jansi)
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
