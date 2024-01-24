plugins {
    id("benchmark.kotlin-conventions")
}

dependencies {
    implementation(libs.jooq.jooq)
    implementation(libs.mariadb)
    implementation(libs.mysql)
    implementation(libs.postgres)
    implementation(libs.testcontainers.mariadb)
    implementation(libs.testcontainers.mysql)
    implementation(libs.testcontainers.postgres)
}