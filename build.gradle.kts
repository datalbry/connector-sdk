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
    from("templates/docs")
    into("docs/userDocs")
    filesMatching("*.mdx") {
        expand(project.properties)
    }
}

tasks.create<Copy>("prepareTutorials") {
    from("templates/tutorials")
    into("docs/tutorials")
    filesMatching("*.mdx") {
        expand(project.properties)
    }
}
