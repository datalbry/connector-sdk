package io.lbrary.connector.autoconfigure.state

import io.lbrary.connector.sdk.state.jpa.DocumentRepository
import io.lbrary.connector.sdk.state.jpa.JobRepository
import io.lbrary.connector.sdk.state.jpa.JpaConnectorDocumentState
import io.lbrary.connector.sdk.state.jpa.LockRepository
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@ConditionalOnClass(JpaConnectorDocumentState::class)
@EnableJpaRepositories
open class JpaConnectorDocumentStateAutoConfiguration {

    @Bean
    open fun jpaConnectorDocumentState(
        jobRepository: JobRepository,
        documentRepository: DocumentRepository,
        lockRepository: LockRepository
    ): JpaConnectorDocumentState {
        return JpaConnectorDocumentState(
            jobRepository,
            documentRepository,
            lockRepository,
        )
    }

}
