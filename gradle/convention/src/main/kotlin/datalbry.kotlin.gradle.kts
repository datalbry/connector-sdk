import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    `java-library`
    idea
    jacoco
}

repositories {
    mavenCentral()
    mavenLocal()
    google()
}

version = getVersion(project)
group = "io.datalbry.connector"

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

// Do not generate reports for individual projects
tasks.getByName("jacocoTestReport") {
    enabled = false
}

// We are using Kotlin, so wdk about the Java Version onwards, as we are not relying on Java 11+ features
tasks.withType<JavaCompile> {
    sourceCompatibility = "1.8"
    targetCompatibility = "1.8"
}

// We are using jUnit5 as default
tasks.withType<Test> {
    useJUnitPlatform()
}

java {
    withJavadocJar()
    withSourcesJar()
}

// Function to get the version of the archive
fun getVersion(project: Project): String {
    var version = project.version as String
    var parent = project.parent
    while (parent != null) {
        version = parent.version as String
        parent = parent.parent
    }
    return version
}


