import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("datalbry.kotlin")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("org.jetbrains.kotlin.plugin.allopen")
    id("org.jetbrains.kotlin.plugin.noarg")
    id("org.jetbrains.kotlin.plugin.spring")
    id("maven-publish")
    id("signing")
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

noArg {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            val group = project.group as String
            artifactId = "${group.substringAfterLast(".")}-${project.name}"
            from(components["java"])
            pom {
                name.set("Connector SDK")
                description.set("The Connector SDK is a Software Development Kit which enables user to implement " +
                        "an Alxndria Connector in no time")
                url.set("https://github.com/datalbry/connector-sdk")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("datalbry")
                        name.set("DataLbry")
                        email.set("devops@datalbry.io")
                    }
                }
                scm {
                    connection.set("https://github.com/datalbry/connector-sdk.git")
                    developerConnection.set("scm:git:ssh:git@github.com:datalbry/connector-sdk.git")
                    url.set("https://github.com/datalbry/connector-sdk")
                }
            }
        }
    }
}

configure<SigningExtension> {
    useGpgCmd()
    sign(publishing.publications["mavenJava"])
}

// ===========================================================================================
// The org.springframework.boot plugin enables the bootJar task which builds an executable jar
// Instead the dependencies are meant to be library modules which rely on spring boot
// so we need to disable building the executable jar and enable the library-jar
tasks.getByName<Jar>("jar") {
    enabled = true
}

tasks.getByName<BootJar>("bootJar") {
    enabled = false
}
// ===========================================================================================
