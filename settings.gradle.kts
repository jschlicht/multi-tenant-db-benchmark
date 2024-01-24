plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "multi-tenant-db-benchmark"
includeBuild("build-conventions")
include("core")
include("data")
include("definition")
include("query")
include("model")
