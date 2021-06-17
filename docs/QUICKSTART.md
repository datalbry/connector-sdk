# Getting Started

The Connector-SDK is primarily for DataLbry internal usage, nevertheless we are releasing the SDK with Open-Source licenses, so others can use it for their very own Connectors, or write new Connectors for the DataLbry Knowledge Cloud.

## Quickstart

1. Add the plugin to your `build.gradle.kts`

```kotlin
plugins {
    id("io.datalbry.connector.sdk") version "0.0.17"
}
```

2. Add the following snippet to your `settings.gradle.kts`
```kotlin
pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "io.datalbry.connector.sdk") {
                useModule("io.datalbry.connector:connector-sdk-plugin:${requested.version}")
            }
        }
    }
}
```

3.

## Gradle Plugin
The Connector-SDK provides a convenience plugin for JVM based builds. 
We developed the Connector-SDK Gradle Plugin to help developer easily bootstrap the setup process of the connector repository.

## Configuration

## Documents
