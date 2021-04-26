import org.jetbrains.kotlin.util.suffixIfNot

plugins {
    id("maven-publish")
    id("signing")
}

publishing {
    publications {
        create<MavenPublication>("snapshot") {
            val projectVersion = project.version as String
            publication(projectVersion.suffixIfNot("-SNAPSHOT"))
            pom()
        }
        create<MavenPublication>("release") {
            val projectVersion = project.version as String
            publication(projectVersion.removeSuffix("-SNAPSHOT"))
            pom()
        }
    }
}

configure<SigningExtension> {
    useGpgCmd()
    sign(publishing.publications["snapshot"])
    sign(publishing.publications["release"])
}

fun MavenPublication.pom() {
    pom {
        name.set("Connector SDK")
        description.set(
            "The Connector SDK is a Software Development Kit which enables user to implement " +
                    "an Alxndria Connector in no time"
        )
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

fun MavenPublication.publication(publicationVersion: String) {
    val projectGroup = project.group as String
    val projectVersion = project.version as String
    artifactId = "${projectGroup.substringAfterLast(".")}-${project.name}"
    version = publicationVersion
    from(components["java"])
}

