package io.datalbry.connector.plugin

import org.gradle.internal.impldep.org.apache.commons.io.FileUtils
import org.gradle.testkit.runner.GradleRunner
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.*


class ConnectorPluginTest {

    @TempDir lateinit var testProjectDir: File
    lateinit var settingsFile: File
    lateinit var buildFile: File

    @BeforeEach
    fun setup() {
        settingsFile = File(testProjectDir, "settings.gradle")
        buildFile = File(testProjectDir, "build.gradle")
    }

    @Test
    fun test() {
        val buildGradle = ConnectorPluginTest::class.java.getResourceAsStream("/case/sunny/build.gradle.kts")
        val settingsGradle = ConnectorPluginTest::class.java.getResourceAsStream("/case/sunny/settings.gradle.kts")
        FileUtils.copyInputStreamToFile(settingsGradle, settingsFile)
        FileUtils.copyInputStreamToFile(buildGradle, buildFile)

        val result = GradleRunner
            .create()
            .withProjectDir(testProjectDir)
            .withPluginClasspath()
            .build()

        println(result)
    }
}
