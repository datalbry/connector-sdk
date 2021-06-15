package io.datalbry.connector.plugin

import org.gradle.internal.impldep.junit.framework.Assert.assertEquals
import org.gradle.internal.impldep.org.apache.commons.io.FileUtils
import org.gradle.testkit.runner.GradleRunner
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.*

import org.gradle.testkit.runner.TaskOutcome.SUCCESS


class ConnectorPluginTest {

    @TempDir lateinit var testProjectDir: File
    private lateinit var settingsFile: File
    private lateinit var buildFile: File

    @BeforeEach
    fun setup() {
        settingsFile = File(testProjectDir, "settings.gradle.kts")
        buildFile = File(testProjectDir, "build.gradle.kts")
    }

    @Test
    fun `assert extension update is being respected`() {
        val buildGradle = ConnectorPluginTest::class.java.getResourceAsStream("/case/override/build.gradle.kts")
        val settingsGradle = ConnectorPluginTest::class.java.getResourceAsStream("/case/override/settings.gradle.kts")
        FileUtils.copyInputStreamToFile(settingsGradle, settingsFile)
        FileUtils.copyInputStreamToFile(buildGradle, buildFile)

        val result = GradleRunner
            .create()
            .withProjectDir(testProjectDir)
            .withPluginClasspath()
            .withArguments("assertExtensionIsBeingUpdated")
            .build()

        assertEquals(SUCCESS, result.task(":assertExtensionIsBeingUpdated")!!.outcome)
    }

    @Test
    fun `assert extension default is being respected`() {
        val buildGradle = ConnectorPluginTest::class.java.getResourceAsStream("/case/default/build.gradle.kts")
        val settingsGradle = ConnectorPluginTest::class.java.getResourceAsStream("/case/default/settings.gradle.kts")
        FileUtils.copyInputStreamToFile(settingsGradle, settingsFile)
        FileUtils.copyInputStreamToFile(buildGradle, buildFile)

        val result = GradleRunner
            .create()
            .withProjectDir(testProjectDir)
            .withPluginClasspath()
            .withArguments("assertExtensionRespectsDefaults")
            .build()

        assertEquals(SUCCESS, result.task(":assertExtensionRespectsDefaults")!!.outcome)
    }
}
