plugins {
    id("datalbry.spring")
    id("datalbry.publish-maven-central")
}

dependencies {
    api(libs.junit)
    api(libs.bundles.testcontainers)
    implementation(libs.postgres)
    implementation(libs.datalbry.alxndria.testcontainer)
}
