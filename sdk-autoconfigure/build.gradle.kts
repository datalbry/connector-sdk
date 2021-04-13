plugins {
    id("datalbry.spring")
    id("datalbry.publish-maven-central")
}

dependencies {
    compileOnly(project(":sdk-api"))
    compileOnly(project(":sdk"))

    compileOnly(libs.datalbry.alxndria.client)
    compileOnly(libs.jakarata.jms.api)
    compileOnly(libs.spring.jms)
    compileOnly(libs.spring.data.jpa)
    compileOnly(libs.jackson.kotlin)

    implementation(libs.spring.autoconfigure)
}
