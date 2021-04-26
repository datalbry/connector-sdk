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

version = "0.0.10"
