plugins {
    id("benchmark.kotlin-conventions")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":definition"))
    implementation(libs.jooq.codegen)
    implementation(libs.jooq.jooq)
    implementation(libs.jooq.meta)
    implementation(libs.postgres)
    implementation(libs.testcontainers.postgres)
}