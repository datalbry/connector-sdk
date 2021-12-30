plugins {
    id("datalbry.spring")
    id("datalbry.publication")
    id("datalbry.publish-internal")
    id("datalbry.publish-maven-central")
}

dependencies {
    compileOnly(project(":sdk-api"))
    compileOnly(project(":sdk"))

    compileOnly(libs.datalbry.alxndria.client)
    compileOnly(libs.spring.amqp)
    compileOnly(libs.spring.data.jpa)
    compileOnly(libs.jackson.kotlin)

    implementation(libs.spring.autoconfigure)
}
