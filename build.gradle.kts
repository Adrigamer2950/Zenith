plugins {
    kotlin("jvm")
}

group = "me.devadri.zenith"
version = property("version") as String

repositories {
    mavenCentral()
}

dependencies {

}

kotlin {
    jvmToolchain(11)
}
