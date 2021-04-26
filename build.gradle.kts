plugins {
    id("datalbry.publish-maven-central")
    idea
}

subprojects {
    repositories {
        mavenCentral()
        mavenLocal()
    }
}

group = "io.datalbry.connector"
version = "0.0.10"
