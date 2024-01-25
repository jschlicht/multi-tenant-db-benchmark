import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import org.gradle.kotlin.dsl.kotlin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    id("benchmark.java-conventions")
    kotlin("jvm")
    id("io.gitlab.arturbosch.detekt")
}

val libs = the<LibrariesForLibs>()

dependencies {
    detektPlugins(libs.detekt.formatting)
    testImplementation(libs.kotest.assertions.core)
    implementation(libs.kotlinLoggingJvm)
}

tasks.withType<KotlinCompile>().all {
    compilerOptions {
        languageVersion.set(KotlinVersion.KOTLIN_2_0)
    }
}

detekt {
    allRules = false
    autoCorrect = true
    buildUponDefaultConfig = true
    config.setFrom("$rootDir/config/detekt.yml")
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