plugins {
    id("datalbry.kotlin")
    id("datalbry.publication")
    id("java-gradle-plugin")
}

repositories {
    gradlePluginPortal()
    mavenCentral()
    google()
}

dependencies {
    testImplementation(gradleTestKit())
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)

    implementation("org.apache.httpcomponents:httpclient:4.5.13")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.12.3")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.3")
    implementation("org.springframework.boot:spring-boot-gradle-plugin:2.3.5.RELEASE")
    implementation("io.spring.gradle:dependency-management-plugin:1.0.9.RELEASE")
    implementation("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:1.5.21-1.0.0-beta06")
    implementation("gradle.plugin.com.google.cloud.tools:jib-gradle-plugin:3.1.4")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
}

gradlePlugin {
    plugins {
        create("connector-sdk") {
            id = "io.datalbry.connector.sdk"
            implementationClass = "io.datalbry.connector.plugin.ConnectorPlugin"
        }
    }
}

// ------------------------------------------------------------------------
// Gradle does not allow to programmatically access the version
// But the plugin requires the version to add the correct versions of the connector-sdk preemptively
// The most simplistic solution is simply to write a properties file, whenever compiling the plugin,
// which then can be consumed by the plugin
val values = tasks.create<Copy>("writeVersionPropertiesToResources") {
    from("templates/version.properties")
    into("src/main/resources")
    expand(project.properties)
    outputs.upToDateWhen { false }
}

tasks.findByName("compileKotlin")!!.dependsOn(values)
// ------------------------------------------------------------------------
