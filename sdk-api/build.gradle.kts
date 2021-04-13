import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("datalbry.spring")
    id("datalbry.publish-maven-central")
}

dependencies {
    api(libs.datalbry.precise.api)
    api(libs.datalbry.commons.config.api)

    testImplementation(libs.spring.test)
}
