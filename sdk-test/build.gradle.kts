plugins {
    id("datalbry.spring")
    id("datalbry.publication")
    id("datalbry.publish-internal")
    id("datalbry.publish-maven-central")
}

dependencies {
    api(project(":sdk"))
    api(libs.bundles.testing)
    api(libs.datalbry.precise.core)
    api(libs.datalbry.alxndria.client)

    implementation(libs.postgres)
    implementation(libs.testcontainers.rabbitmq)
    implementation(libs.datalbry.alxndria.testcontainer)
    implementation(libs.kotlin.stdlib)

    runtimeOnly(libs.kotlin.reflect)

    testRuntimeOnly(project(":sdk-autoconfigure"))

    // TODO Delete me
    testImplementation(libs.spring.amqp)
}
