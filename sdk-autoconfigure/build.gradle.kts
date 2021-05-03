plugins {
    id("datalbry.spring")
}

dependencies {
    compileOnly(project(":sdk-api"))
    compileOnly(project(":sdk"))

    compileOnly(libs.datalbry.alxndria.client)
    compileOnly(libs.jakarata.jms.api)
    compileOnly(libs.spring.amqp)
    compileOnly(libs.spring.data.jpa)
    compileOnly(libs.jackson.kotlin)

    implementation(libs.spring.autoconfigure)
}
