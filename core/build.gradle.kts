plugins {
    id("benchmark.kotlin-conventions")
}

dependencies {
    implementation(libs.jooq.jooq)
    implementation(libs.testcontainers.mariadb)
    implementation(libs.testcontainers.mysql)
    implementation(libs.testcontainers.postgres)
}