plugins {
    application
    id("benchmark.kotlin-conventions")
}

repositories {
    mavenCentral()
}

group = "com.github.jschlicht"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(project(":core"))
    implementation(libs.clikt)
    implementation(libs.faker)
    implementation(libs.jooq.jooq)
    implementation(libs.mariadb)
    implementation(libs.mysql)
    implementation(libs.postgres)
    implementation(libs.testcontainers.mariadb)
    implementation(libs.testcontainers.mysql)
    implementation(libs.testcontainers.postgres)
}

application {
    mainClass = "com.github.jschlicht.multitenantdbbenchmark"
}