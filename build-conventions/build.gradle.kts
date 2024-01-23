plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.gradleplugin.detekt)
    implementation(libs.gradleplugin.kotlin.jvm)

    // https://github.com/gradle/gradle/issues/15383
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

repositories {
    gradlePluginPortal()
}