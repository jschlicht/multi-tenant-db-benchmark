import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    application
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.detekt)
}

group = "com.github.jschlicht"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    detektPlugins(libs.detekt.formatting)
    implementation(libs.apache.commons.csv)
    implementation(libs.apache.commons.io)
    implementation(libs.clikt)
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
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.junit.jupter.api)
    testRuntimeOnly(libs.junit.jupter.engine)
    implementation(libs.kotlinLoggingJvm)
    implementation(libs.logback.classic)
    implementation(libs.mariadb)
    implementation(libs.mysql)
    implementation(libs.postgres)
    implementation(libs.slf4j.api)
    implementation(libs.testcontainers.mariadb)
    implementation(libs.testcontainers.mysql)
    implementation(libs.testcontainers.postgres)
}

tasks.test {
    useJUnitPlatform()
}


detekt {
    allRules = false // activate all available (even unstable) rules.
    autoCorrect = true
    buildUponDefaultConfig = true // preconfigure defaults
    config.setFrom("$projectDir/config/detekt.yml")
    parallel = true
}


tasks.withType<Detekt>().configureEach {
    reports {
        html.required.set(true)
        xml.required.set(true)
        txt.required.set(true)
        sarif.required.set(true)
        md.required.set(true)
    }
}

tasks.withType<Detekt>().configureEach {
    jvmTarget = libs.versions.jdk.get()
}
tasks.withType<DetektCreateBaselineTask>().configureEach {
    jvmTarget = libs.versions.jdk.get()
}

kotlin {
    jvmToolchain(libs.versions.jdk.get().toInt())
    compilerOptions {
        languageVersion.set(KotlinVersion.KOTLIN_2_0)
    }
}

application {
    mainClass = "com.github.jschlicht.multitenantdbbenchmark"
}