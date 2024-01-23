import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    java
}

val libs = the<LibrariesForLibs>()

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(libs.junit.jupter.api)
    testRuntimeOnly(libs.junit.jupter.engine)
    implementation(libs.logback.classic)
    implementation(libs.slf4j.api)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(libs.versions.jdk.get()))
    }
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-Xlint:deprecation")
}

tasks.withType<Test> {
    useJUnitPlatform()
}