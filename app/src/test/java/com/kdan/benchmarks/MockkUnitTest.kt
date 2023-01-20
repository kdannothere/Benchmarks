package com.kdan.benchmarks

import com.kdan.benchmarks.repository.CollectionsRep
import com.kdan.benchmarks.repository.MapsRep
import io.mockk.*
import org.junit.Assert.assertEquals
import org.junit.Test

const val ONE_MILLION = 1_000_000

class MockkUnitTest {

    private val collRep = mockk<CollectionsRep>(relaxed = true)
    private val mapsRep = mockk<MapsRep>(relaxed = true)

    @Test
    fun test1() {
        collRep.createArrayList(ONE_MILLION)
        val captor = slot<Int>()
        verify { collRep.createArrayList(capture(captor)) }
        assertEquals(ONE_MILLION, captor.captured)
    }

    @Test
    fun test2() {
        collRep.createLinkedList(ONE_MILLION)
        val captor = slot<Int>()
        verify { collRep.createLinkedList(capture(captor)) }
        assertEquals(ONE_MILLION, captor.captured)
    }

    @Test
    fun test3() {
        collRep.createCopyOnWriteArrayList(ONE_MILLION)
        val captor = slot<Int>()
        verify { collRep.createCopyOnWriteArrayList(capture(captor)) }
        assertEquals(ONE_MILLION, captor.captured)
    }

    @Test
    fun test4() {
        mapsRep.createTreeMap(ONE_MILLION)
        val captor = slot<Int>()
        verify { mapsRep.createTreeMap(capture(captor)) }
        assertEquals(ONE_MILLION, captor.captured)
    }

    @Test
    fun test5() {
        mapsRep.createHashMap(ONE_MILLION)
        val captor = slot<Int>()
        verify { mapsRep.createHashMap(capture(captor)) }
        assertEquals(ONE_MILLION, captor.captured)
    }

    @Test
    fun test6() {
        collRep.addingBeginningArrayList(0)
        verify { collRep.addingBeginningArrayList(0) }
    }

    @Test
    fun test7() {
        collRep.addingMiddleArrayList(0)
        verify { collRep.addingMiddleArrayList(0) }
    }

    @Test
    fun test8() {
        collRep.addingEndArrayList(0)
        verify { collRep.addingEndArrayList(0) }
    }

    @Test
    fun test9() {
        collRep.searchByValueArrayList(0)
        verify { collRep.searchByValueArrayList(0) }
    }

    @Test
    fun test10() {
        collRep.removingBeginningArrayList(0)
        verify { collRep.removingBeginningArrayList(0) }
    }

    @Test
    fun test11() {
        collRep.removingMiddleArrayList(0)
        verify { collRep.removingMiddleArrayList(0) }
    }

    @Test
    fun test12() {
        collRep.removingEndArrayList(0)
        verify { collRep.removingEndArrayList(0) }
    }

    @Test
    fun test13() {
        collRep.addingBeginningLinkedList(0)
        verify { collRep.addingBeginningLinkedList(0) }
    }

    @Test
    fun test14() {
        collRep.addingMiddleLinkedList(0)
        verify { collRep.addingMiddleLinkedList(0) }
    }

    @Test
    fun test15() {
        collRep.addingEndLinkedList(0)
        verify { collRep.addingEndLinkedList(0) }
    }

    @Test
    fun test16() {
        collRep.searchByValueLinkedList(0)
        verify { collRep.searchByValueLinkedList(0) }
    }

    @Test
    fun test17() {
        collRep.removingBeginningLinkedList(0)
        verify { collRep.removingBeginningLinkedList(0) }
    }

    @Test
    fun test18() {
        collRep.removingMiddleLinkedList(0)
        verify { collRep.removingMiddleLinkedList(0) }
    }

    @Test
    fun test19() {
        collRep.removingEndLinkedList(0)
        verify { collRep.removingEndLinkedList(0) }
    }

    @Test
    fun test20() {
        collRep.addingBeginningCopyOnWriteArrayList(0)
        verify { collRep.addingBeginningCopyOnWriteArrayList(0) }
    }

    @Test
    fun test21() {
        collRep.addingMiddleCopyOnWriteArrayList(0)
        verify { collRep.addingMiddleCopyOnWriteArrayList(0) }
    }

    @Test
    fun test22() {
        collRep.addingEndCopyOnWriteArrayList(0)
        verify { collRep.addingEndCopyOnWriteArrayList(0) }
    }

    @Test
    fun test23() {
        collRep.searchByValueCopyOnWriteArrayList(0)
        verify { collRep.searchByValueCopyOnWriteArrayList(0) }
    }

    @Test
    fun test24() {
        collRep.removingBeginningCopyOnWriteArrayList(0)
        verify { collRep.removingBeginningCopyOnWriteArrayList(0) }
    }

    @Test
    fun test25() {
        collRep.removingMiddleCopyOnWriteArrayList(0)
        verify { collRep.removingMiddleCopyOnWriteArrayList(0) }
    }

    @Test
    fun test26() {
        collRep.removingEndCopyOnWriteArrayList(0)
        verify { collRep.removingEndCopyOnWriteArrayList(0) }
    }

    @Test
    fun test27() {
        mapsRep.addingTreeMap(0)
        verify { mapsRep.addingTreeMap(0) }
    }

    @Test
    fun test28() {
        mapsRep.searchingTreeMap(0)
        verify { mapsRep.searchingTreeMap(0) }
    }

    @Test
    fun test29() {
        mapsRep.removingTreeMap(0)
        verify { mapsRep.removingTreeMap(0) }
    }

    @Test
    fun test30() {
        mapsRep.addingHashMap(0)
        verify { mapsRep.addingHashMap(0) }
    }

    @Test
    fun test31() {
        mapsRep.searchingHashMap(0)
        verify { mapsRep.searchingHashMap(0) }
    }

    @Test
    fun test32() {
        mapsRep.removingHashMap(0)
        verify { mapsRep.removingHashMap(0) }
    }

}