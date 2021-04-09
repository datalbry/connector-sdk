import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("datalbry.kotlin")
    id("datalbry.publish-maven-central")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

// Set API version to parents major version
version = project.parent!!.version.toString().split(".")[0]

// ===========================================================================================
// The org.springframework.boot plugin enables the bootJar task which builds an executable jar
// Instead the Platform is a library module which relies on spring boot
// so we need to disable building the executable jar and enable the default jar
tasks.getByName<Jar>("jar") {
    enabled = true
}

tasks.getByName<BootJar>("bootJar") {
    enabled = false
}
// ===========================================================================================

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))

    api("io.datalbry.config-schema:api:0.0.1")
    api("io.datalbry.precise:api:0.0.4")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
