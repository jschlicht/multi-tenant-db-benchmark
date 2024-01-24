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
    implementation(project(":definition"))
    implementation(project(":data"))
    implementation(project(":model"))
    implementation(libs.clikt)
}

application {
    mainClass = "com.github.jschlicht.multitenantdbbenchmark"
}