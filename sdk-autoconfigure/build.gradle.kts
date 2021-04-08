import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("jvm")
    kotlin("kapt")
    id("java-library")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

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

    compileOnly(project(":sdk-api"))
    compileOnly(project(":sdk"))

    compileOnly("io.datalbry.alxndria:client-feign:0.0.1")
    compileOnly("jakarta.jms:jakarta.jms-api:2.0.3")
    compileOnly("org.springframework:spring-jms")
    compileOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
    compileOnly("org.springframework.data:spring-data-jpa")

    implementation("org.springframework.boot:spring-boot-autoconfigure")
}
