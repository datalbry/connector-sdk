plugins {
    id("datalbry.publish-maven-lock")
    idea
}

subprojects {
    repositories {
        mavenCentral()
        mavenLocal()
    }
}

extra["springCloudVersion"] = "Hoxton.SR6"

version = "0.0.5-SNAPSHOT"
