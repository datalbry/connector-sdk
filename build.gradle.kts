plugins {
    id("datalbry.publish-maven-central")
    id("datalbry.version")
    idea
}

subprojects {
    repositories {
        mavenCentral()
        mavenLocal()
    }
}

group = "io.datalbry.connector"

tasks.create<Copy>("prepareDocs") {
    from("templates")
    into("docs")
    filesMatching("*.mdx") {
        expand(project.properties)
    }
}
