import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm")
    alias(libs.plugins.shadow)
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
    mainClass.set("me.adrigamer2950.zenith.example.MainKt")
    classpath = sourceSets["exampleMain"].runtimeClasspath
}
