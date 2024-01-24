plugins {
    id("benchmark.kotlin-conventions")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":model"))
    implementation(libs.faker)
    implementation(libs.jooq.jooq)
}