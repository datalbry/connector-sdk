plugins {
    id("datalbry.spring")
    id("datalbry.publish-maven-central")
}

dependencies {
    api(project(":sdk"))
    api(libs.junit)
    api(libs.bundles.testing)
    api(libs.datalbry.precise.core)
    api(libs.datalbry.alxndria.client)
    implementation(libs.apache.jms.artemis)
    implementation(libs.postgres)
    implementation(libs.datalbry.alxndria.testcontainer)
}
