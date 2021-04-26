package io.datalbry.connector.sdk

import io.datalbry.connector.sdk.ConnectorProperties.AlxndriaProperties
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.context.properties.ConfigurationProperties

internal class ConnectorPropertiesTest {

    @Test
    fun checkConstants_referencingTheImplicitName() {
        val springPropertiesAnnotation = ConnectorProperties::class.annotations.filterIsInstance<ConfigurationProperties>().first()
        val prefix = springPropertiesAnnotation.prefix.ifEmpty { springPropertiesAnnotation.value }

        val concurrencyProperty = "$prefix.${ConnectorProperties::concurrency.name}"
        assertEquals(concurrencyProperty, ConnectorProperties.CONCURRENCY_PROPERTY)

        val datasourceProperty = "$prefix.${ConnectorProperties::alxndria.name}.${AlxndriaProperties::datasource.name}"
        assertEquals(datasourceProperty, ConnectorProperties.ALXNDRIA_DATASOURCE_PROPERTY)

        val uriProperty = "$prefix.${ConnectorProperties::alxndria.name}.${AlxndriaProperties::uri.name}"
        assertEquals(uriProperty, ConnectorProperties.ALXNDRIA_URI_PROPERTY)
    }

}
