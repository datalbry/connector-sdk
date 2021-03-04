package io.lbrary.connector.sdk.state.jpa

import io.lbrary.connector.sdk.state.jpa.util.createDocumentIds
import io.lbrary.connector.sdk.state.jpa.util.createNode
import io.lbrary.connector.sdk.state.jpa.util.createNodes
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Disabled

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.StopWatch
import org.testcontainers.junit.jupiter.Testcontainers
import java.time.Duration

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
internal class JpaConnectorDocumentStateTest {

    @Autowired private lateinit var jpaConnectorDocumentState: JpaConnectorDocumentState

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    fun putNodes_supports_parallelism() {
        val root = createNode()
        val childrenFirstSync = createNodes(0, 100)

        val lock1 = jpaConnectorDocumentState.lock(root)
        childrenFirstSync
            .parallelStream()
            .forEach { assertDoesNotThrow { jpaConnectorDocumentState.put(root, it, lock1) } }

        jpaConnectorDocumentState.release(root, lock1)
        val lock2 = jpaConnectorDocumentState.lock(root)
        assertTrue(jpaConnectorDocumentState.getUnseenNodes(root, lock2).containsAll(childrenFirstSync))
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    fun putNodes_iteratingGreatAmountOfDocumentsForSameRoot_isStable() {
        val root = createNode()
        val maxDurationPerNode = Duration.ofMillis(10)
        val childrenFirstSync = createNodes(0, 15_000)

        val lockSync1 = jpaConnectorDocumentState.lock(root)
        val watch = StopWatch()
        watch.start()
        childrenFirstSync
            .parallelStream()
            .forEach { jpaConnectorDocumentState.put(root, it, lockSync1) }
        watch.stop()
        assertDoesNotThrow { jpaConnectorDocumentState.release(root, lockSync1) }

        val average = watch.totalTimeMillis / childrenFirstSync.size
        assertTrue {average  < maxDurationPerNode.toMillis() }
        log.info("Putting ${childrenFirstSync.size} Nodes took ${watch.totalTimeMillis}ms in" +
                " total and an average of ${average}ms per Node")
    }

    @Test
    @Disabled
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    fun putNodes_iteratingHugeAmountOfDocumentsForSameRoot_isStable() {
        val root = createNode()
        val maxDurationPerNode = Duration.ofMillis(10)
        val childrenFirstSync = createNodes(0, 250_000)

        val lockSync1 = jpaConnectorDocumentState.lock(root)
        val watch = StopWatch()
        watch.start()
        childrenFirstSync
            .parallelStream()
            .forEach { jpaConnectorDocumentState.put(root, it, lockSync1) }
        watch.stop()
        assertDoesNotThrow { jpaConnectorDocumentState.release(root, lockSync1) }

        val average = watch.totalTimeMillis / childrenFirstSync.size
        assertTrue {average  < maxDurationPerNode.toMillis() }
        log.info("Putting ${childrenFirstSync.size} Nodes took ${watch.totalTimeMillis}ms in" +
                " total and an average of ${average}ms per Node")
    }

    @Test
    fun getUnseenNodes_withSubsequentSync_containsTheCorrectAmountOfUnseenNodes() {
        val firstStart = 0
        val firstEnd = 35
        val secondStart = 23
        val secondEnd = 60

        val root = createNode()

        val childrenFirstSync = createNodes(firstStart, firstEnd)
        val childrenSecondSync = createNodes(secondStart, secondEnd)

        val lockSync1 = jpaConnectorDocumentState.lock(root)
        childrenFirstSync.forEach { jpaConnectorDocumentState.put(root, it, lockSync1) }
        jpaConnectorDocumentState.release(root, lockSync1)

        val lockSync2 = jpaConnectorDocumentState.lock(root)
        childrenSecondSync.forEach { jpaConnectorDocumentState.put(root, it, lockSync2) }
        val sync2Unseen = jpaConnectorDocumentState.getUnseenNodes(root, lockSync2)
        jpaConnectorDocumentState.release(root, lockSync2)

        assertTrue(sync2Unseen.size == secondStart - firstStart)
    }

    @Test
    fun getUnseenNodes_ofChildrenFirstSync_doesNotContainAnyUnseenNode() {
        val firstStart = 0
        val firstEnd = 35
        val secondStart = 23
        val secondEnd = 60

        val root = createNode()

        val childrenFirstSync = createNodes(firstStart, firstEnd)
        val childrenSecondSync = createNodes(secondStart, secondEnd)

        val lockSync1 = jpaConnectorDocumentState.lock(root)
        childrenFirstSync.forEach { jpaConnectorDocumentState.put(root, it, lockSync1) }
        jpaConnectorDocumentState.release(root, lockSync1)

        val sync2parent = childrenFirstSync.random()
        val lockSync2 = jpaConnectorDocumentState.lock(sync2parent)
        childrenSecondSync.forEach { jpaConnectorDocumentState.put(sync2parent, it, lockSync2) }
        val sync2Unseen = jpaConnectorDocumentState.getUnseenNodes(sync2parent, lockSync2)
        jpaConnectorDocumentState.release(sync2parent, lockSync2)

        assertTrue(sync2Unseen.isEmpty())
    }

    @Test
    fun getUnseenDocument_ofChildrenFirstSync_doesNotContainAnyUnseenDocuments() {
        val firstStart = 0
        val firstEnd = 35
        val secondStart = 23
        val secondEnd = 60

        val root = createNode()

        val childrenFirstSync = createDocumentIds(firstStart, firstEnd)
        val childrenSecondSync = createDocumentIds(secondStart, secondEnd)

        val sync2parent = createNode()
        val lockSync1 = jpaConnectorDocumentState.lock(root)
        childrenFirstSync.forEach { jpaConnectorDocumentState.put(root, it, lockSync1) }
        jpaConnectorDocumentState.put(root, sync2parent, lockSync1)
        jpaConnectorDocumentState.release(root, lockSync1)

        val lockSync2 = jpaConnectorDocumentState.lock(sync2parent)
        childrenSecondSync.forEach { jpaConnectorDocumentState.put(sync2parent, it, lockSync2) }
        val sync2Unseen = jpaConnectorDocumentState.getUnseenNodes(sync2parent, lockSync2)
        jpaConnectorDocumentState.release(sync2parent, lockSync2)

        assertTrue(sync2Unseen.isEmpty())
    }

    @Test
    fun getUnseenDocument_withSubsequentSync_containsTheCorrectAmountOfUnseenDocs() {
        val firstStart = 0
        val firstEnd = 35
        val secondStart = 23
        val secondEnd = 60

        val root = createNode()

        val childrenFirstSync = createDocumentIds(firstStart, firstEnd)
        val childrenSecondSync = createDocumentIds(secondStart, secondEnd)

        val lockSync1 = jpaConnectorDocumentState.lock(root)
        childrenFirstSync.forEach { jpaConnectorDocumentState.put(root, it, lockSync1) }
        jpaConnectorDocumentState.release(root, lockSync1)

        val lockSync2 = jpaConnectorDocumentState.lock(root)
        childrenSecondSync.forEach { jpaConnectorDocumentState.put(root, it, lockSync2) }
        val sync2Unseen = jpaConnectorDocumentState.getUnseenDocuments(root, lockSync2)
        jpaConnectorDocumentState.release(root, lockSync2)

        assertTrue(sync2Unseen.size == secondStart - firstStart)
    }

    companion object {
        private val log = LoggerFactory.getLogger(JpaConnectorDocumentStateTest::class.java)
    }
}
