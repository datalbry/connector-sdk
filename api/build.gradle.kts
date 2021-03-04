import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("jvm")
    id("java-library")
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

    api(project(":platform:service:index:api"))

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
