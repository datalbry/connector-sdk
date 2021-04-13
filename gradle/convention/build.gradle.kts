plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin")
    implementation("org.springframework.boot:spring-boot-gradle-plugin:2.3.5.RELEASE")
    implementation("io.spring.gradle:dependency-management-plugin:1.0.9.RELEASE")
    implementation("org.jetbrains.kotlin:kotlin-allopen:1.4.31")
    implementation("org.jetbrains.kotlin:kotlin-noarg:1.4.31")
}
