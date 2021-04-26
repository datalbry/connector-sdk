plugins {
    id("datalbry.spring")
    id("datalbry.publish-maven-central")
}

dependencies {
    api(project(":sdk"))
    api(libs.bundles.testing)
    api(libs.datalbry.precise.core)
    api(libs.datalbry.alxndria.client)

    implementation(libs.apache.jms.artemis)
    implementation(libs.postgres)
    implementation(libs.datalbry.alxndria.testcontainer)
    implementation(libs.kotlin.stdlib)

    runtimeOnly(libs.kotlin.reflect)
}
