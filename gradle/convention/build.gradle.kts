plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

val kotlinVersion = "1.5.31"

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin")
    implementation("org.springframework.boot:spring-boot-gradle-plugin:2.3.5.RELEASE")
    implementation("io.spring.gradle:dependency-management-plugin:1.0.9.RELEASE")
    implementation("io.github.gradle-nexus:publish-plugin:1.1.0")
    implementation("org.jetbrains.kotlin:kotlin-allopen:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-noarg:$kotlinVersion")
}
