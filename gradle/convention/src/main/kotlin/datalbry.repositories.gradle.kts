import org.gradle.kotlin.dsl.repositories

val registryUrl = findPropertyOrEnv("maven.registry")
fun findPropertyOrEnv(property: String): String? {
    return (project.findProperty(property) as String?)
        ?: System.getenv(property.replace('.', '_').toUpperCase())
}
repositories {
    registryUrl?.let {
        maven {
            url = uri(registryUrl)
            credentials {
                username = findPropertyOrEnv("maven.registry.username")
                password = findPropertyOrEnv("maven.registry.password")
            }
        }
    }

    mavenCentral()
    google()
}
