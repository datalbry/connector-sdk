package io.datalbry.connector.sdk.test

import io.datalbry.connector.sdk.test.ConnectorTestExtension.Companion.PROPERTY_DATALBRY_PLATFORM_URI
import io.datalbry.connector.sdk.test.ConnectorTestExtension.Companion.PROPERTY_SPRING_DATASOURCE_PASSWORD
import io.datalbry.connector.sdk.test.ConnectorTestExtension.Companion.PROPERTY_SPRING_DATASOURCE_URL
import io.datalbry.connector.sdk.test.ConnectorTestExtension.Companion.PROPERTY_SPRING_DATASOURCE_USERNAME
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(ConnectorTestExtension::class)
internal class ConnectorTestExtensionTest {

    @Test
    fun checkExtensionSettingUpPropertiesCorrectly() {
        System.getProperties().containsKey(PROPERTY_DATALBRY_PLATFORM_URI)
        System.getProperties().containsKey(PROPERTY_SPRING_DATASOURCE_PASSWORD)
        System.getProperties().containsKey(PROPERTY_SPRING_DATASOURCE_URL)
        System.getProperties().containsKey(PROPERTY_SPRING_DATASOURCE_USERNAME)
    }
}
