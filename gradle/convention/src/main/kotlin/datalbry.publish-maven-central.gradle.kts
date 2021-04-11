plugins {
    signing
    `maven-publish`
}

configure<PublishingExtension> {
    publications {
        repositories {
            maven {
                name = "MavenCentral"
                url = if (project.rootProject.version.toString().endsWith("SNAPSHOT")) {
                    uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
                } else {
                    uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                }
                credentials {
                    username = project.findProperty("maven.central.username") as String
                    password = project.findProperty("maven.central.password") as String
                }
            }
        }
        create<MavenPublication>("jar") {
            from(components["java"])
            artifactId = "connector-${project.name}"
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            pom {
                name.set("Connector ${project.name}")
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
    sign(publishing.publications["jar"])
}
