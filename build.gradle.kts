import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    alias(libs.plugins.kotlin.jvm)
}

group = "com.github.jschlicht"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.apache.commons.csv)
    implementation(libs.apache.commons.io)
    implementation(libs.faker)
    implementation(libs.guava)
    implementation(platform(libs.jackson.bom))
    implementation(libs.jackson.databind)
    implementation(libs.jackson.datatype.jdk8)
    implementation(libs.jackson.datatype.jsr310)
    implementation(libs.jackson.module.kotlin)
    implementation(libs.jackson.module.parameterNames)
    implementation(libs.jooq.jooq)
    implementation(libs.jooq.postgresExtensions)
    implementation(libs.logback.classic)
    implementation(libs.kotlinLoggingJvm)
    implementation(libs.slf4j.api)
    implementation(libs.mariadb)
    implementation(libs.mysql)
    implementation(libs.postgres)
    implementation(libs.testcontainers.mariadb)
    implementation(libs.testcontainers.mysql)
    implementation(libs.testcontainers.postgres)
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(libs.versions.jdk.get().toInt())
    compilerOptions {
        languageVersion.set(KotlinVersion.KOTLIN_2_0)
    }
}