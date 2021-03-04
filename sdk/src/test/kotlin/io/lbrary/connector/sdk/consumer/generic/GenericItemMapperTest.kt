package io.lbrary.connector.sdk.consumer.generic

import io.lbrary.connector.api.extensions.get
import io.lbrary.connector.sdk.consumer.generic.documents.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.net.URI
import java.time.ZonedDateTime
import java.util.*

internal class GenericItemMapperTest {

    @Test
    fun getDocuments_mapSingleItem_workJustFine() {
        val item = SimpleDocument("unique id", "TestItem", "Just me...", ZonedDateTime.now())
        val mapper = GenericItemMapper(item::class)

        val document = mapper.getDocuments(item).first()
        val id = UUID.nameUUIDFromBytes(item.docId.toByteArray()).toString()
        assertTrue(document.metadata.isNotEmpty())
        assertEquals(document.id.key, id)
        assertEquals(document[SimpleDocument::title.name].value, item.title)
        assertEquals(document[SimpleDocument::author.name].value, item.author)
        assertEquals(document[SimpleDocument::modified.name].value, item.modified)
    }

    @Test
    fun getDocuments_withoutIdAddsSyntheticId_workJustFine() {
        val item = DocumentWithoutId("TestItem", "Just me...", ZonedDateTime.now())
        val mapper = GenericItemMapper(item::class)

        val document = mapper.getDocuments(item).first()
        assertTrue(document.metadata.isNotEmpty())
        assertNotNull(document.id.key)
        assertEquals(document[DocumentWithoutId::title.name].value, item.title)
        assertEquals(document[DocumentWithoutId::author.name].value, item.author)
        assertEquals(document[DocumentWithoutId::modified.name].value, item.modified)
    }

    @Test
    fun getDocuments_documentContainsCollection_workJustFine() {
        val item = DocumentWithCollection("another unique id", "Collection Item", listOf(""), ZonedDateTime.now())
        val mapper = GenericItemMapper(item::class)

        val document = mapper.getDocuments(item).first()
        val id = UUID.nameUUIDFromBytes(item.docId.toByteArray()).toString()
        assertTrue(document.metadata.isNotEmpty())
        assertEquals(document.id.key, id)
        assertEquals(document[DocumentWithCollection::title.name].value, item.title)
        assertEquals(document[DocumentWithCollection::contributors.name].value, item.contributors)
        assertEquals(document[DocumentWithCollection::modified.name].value, item.modified)
    }

    @Test
    fun getDocuments_containingCollectionOfIds_gettingReducedCorrectly() {
        val item = DocumentWithIdCollection(listOf("id1", "id2"), "Collection Item", listOf(""), ZonedDateTime.now())
        val mapper = GenericItemMapper(item::class)

        val document = mapper.getDocuments(item).first()
        val id = UUID.nameUUIDFromBytes(item.docId.reduce { acc, s -> acc + s }.toByteArray()).toString()
        assertTrue(document.metadata.isNotEmpty())
        assertEquals(document.id.key, id)
        assertEquals(document[DocumentWithCollection::title.name].value, item.title)
        assertEquals(document[DocumentWithCollection::contributors.name].value, item.contributors)
        assertEquals(document[DocumentWithCollection::modified.name].value, item.modified)
    }

    @Test
    fun getDocuments_children_haveNoEffectOnGetDocumentsChildren() {
        val childrenInput = listOf(Child("google", URI.create("http://google.com")))
        val item = DocumentWithChildren("id", "Collection Item", ZonedDateTime.now(), childrenInput)
        val mapper = GenericItemMapper(item::class)

        val document = mapper.getDocuments(item).first()
        val id = UUID.nameUUIDFromBytes(item.docId.toByteArray()).toString()
        assertTrue(document.metadata.isNotEmpty())
        assertEquals(document.id.key, id)
        assertEquals(document[DocumentWithChildren::title.name].value, item.title)
        assertEquals(document[DocumentWithChildren::modified.name].value, item.modified)

        val children = mapper.getEdges(item)
        val sourceEdges = childrenInput.map(Child::toEdge)
        assertTrue(children.containsAll(sourceEdges))
    }

    @Test
    fun getEdges_containingChildren_areMappedCorrectly() {
        val childrenInput = listOf(Child("google", URI.create("http://google.com")))
        val container = ContainerWithChildren(children = childrenInput)
        val mapper = GenericItemMapper(container::class)

        val documents = mapper.getDocuments(container)
        assertTrue(documents.isEmpty())

        val children = mapper.getEdges(container)
        val sourceEdges = childrenInput.map(Child::toEdge)
        assertTrue(children.containsAll(sourceEdges))
    }
}

