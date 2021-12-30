plugins {
    id("datalbry.spring")
}

dependencies {
    api(libs.datalbry.precise.api)
    api(libs.datalbry.commons.config.api)

    testImplementation(libs.spring.test)
}
