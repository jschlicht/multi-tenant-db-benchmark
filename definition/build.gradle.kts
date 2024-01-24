plugins {
    id("benchmark.kotlin-conventions")
}

dependencies {
    implementation(project(":core"))
    implementation(libs.jooq.jooq)
}