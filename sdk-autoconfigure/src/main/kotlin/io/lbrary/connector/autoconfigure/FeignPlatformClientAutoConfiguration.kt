package io.lbrary.connector.autoconfigure

import io.lbrary.client.api.DatasourceClient
import io.lbrary.client.api.IndexClient
import io.lbrary.client.api.PlatformClient
import io.lbrary.client.feign.v1.FeignPlatformClient
import io.lbrary.connector.sdk.PlatformProperties
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(PlatformProperties::class)
@ConditionalOnClass(PlatformProperties::class, FeignPlatformClient::class)
open class FeignPlatformClientAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(PlatformClient::class)
    open fun feignPlatformClient(properties: PlatformProperties): FeignPlatformClient {
        return FeignPlatformClient.createDefault(properties.uri.toString())
    }

    @Bean
    @ConditionalOnMissingBean(IndexClient::class)
    open fun feignIndexClient(properties: PlatformProperties): IndexClient {
        return feignPlatformClient(properties).index
    }

    @Bean
    @ConditionalOnMissingBean(DatasourceClient::class)
    open fun feignDatasourceClient(properties: PlatformProperties): DatasourceClient {
        return feignPlatformClient(properties).datasource
    }

}
