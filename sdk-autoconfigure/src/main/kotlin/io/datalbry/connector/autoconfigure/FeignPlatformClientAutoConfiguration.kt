package io.datalbry.connector.autoconfigure

import io.datalbry.alxndria.client.api.DatasourceClient
import io.datalbry.alxndria.client.api.IndexClient
import io.datalbry.alxndria.client.api.PlatformClient
import io.datalbry.alxndria.client.feign.v1.FeignPlatformClient
import io.datalbry.connector.sdk.ConnectorProperties
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(ConnectorProperties::class)
@ConditionalOnClass(ConnectorProperties::class, FeignPlatformClient::class)
open class FeignPlatformClientAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(PlatformClient::class)
    open fun feignPlatformClient(properties: ConnectorProperties): FeignPlatformClient {
        return FeignPlatformClient(properties.uri.toString())
    }

    @Bean
    @ConditionalOnMissingBean(IndexClient::class)
    open fun feignIndexClient(properties: ConnectorProperties): IndexClient {
        return feignPlatformClient(properties).index
    }

    @Bean
    @ConditionalOnMissingBean(DatasourceClient::class)
    open fun feignDatasourceClient(properties: ConnectorProperties): DatasourceClient {
        return feignPlatformClient(properties).datasource
    }

}
