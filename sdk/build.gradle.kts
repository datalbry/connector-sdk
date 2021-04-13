plugins {
    id("datalbry.spring")
    id("datalbry.publish-maven-central")
}

dependencies {
    api(libs.spring.web)
    api(project(":sdk-api"))

    implementation(libs.postgres)
    implementation(libs.spring.data.jpa)
    implementation(libs.spring.artemis)
    implementation(libs.jackson.kotlin)
    implementation(libs.datalbry.alxndria.client)

    testImplementation(libs.bundles.testing)
}
