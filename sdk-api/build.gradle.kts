plugins {
    id("datalbry.spring")
    id("datalbry.publication")
    id("datalbry.publish-internal")
    id("datalbry.publish-maven-central")
}

dependencies {
    api(libs.datalbry.precise.api)
    api(libs.datalbry.commons.config.api)

    testImplementation(libs.spring.test)
}
