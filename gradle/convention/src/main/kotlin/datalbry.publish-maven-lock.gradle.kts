import java.time.Duration
import java.util.concurrent.Semaphore

val semaphore = Semaphore(1)
fun acquireLock() = semaphore.acquire()
fun releaseLock() = semaphore.release()

subprojects {
    repositories {
        mavenCentral()
    }

    val finalizePublishTask = tasks.create("finalizePublish") {
        // TODO
        Thread.sleep(Duration.ofSeconds(1).toMillis())
        releaseLock()
    }

    project.tasks.withType(PublishToMavenRepository::class) {
        doFirst { acquireLock() }
        this.finalizedBy(finalizePublishTask)
    }
}
