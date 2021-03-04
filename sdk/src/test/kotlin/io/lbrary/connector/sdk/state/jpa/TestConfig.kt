package io.lbrary.connector.sdk.state.jpa

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TestConfig {

    @Bean
    fun jpaConnectorDocumentState(job: JobRepository, document: DocumentRepository, lock: LockRepository) =
        JpaConnectorDocumentState(job, document, lock)

}
