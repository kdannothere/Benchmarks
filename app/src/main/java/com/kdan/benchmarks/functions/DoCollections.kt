package com.kdan.benchmarks.functions

import android.content.Context
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.collections.ArrayList
import kotlin.concurrent.thread

class DoCollections (
    collectionSize: Int,
    private val elementsAmount: Int,
    val context: Context?
) {

    private var _collection: List<Int>? = createList(collectionSize)
    private val collection get() = _collection!!
    private var position = 0

    init {
        _collection = createList(collectionSize)
    }

    fun startAll() {
        while (position < 21) {
            thread(name = "Thread $position") {
                when (position) {
                    0 -> addingBeginningArrayList()
                    1 -> addingMiddleArrayList()
                    2 -> addingEndArrayList()
                    3 -> searchByValueArrayList()
                    4 -> removingBeginningArrayList()
                    5 -> removingMiddleArrayList()
                    6 -> removingEndArrayList()
                    7 -> addingBeginningLinkedList()
                    8 -> addingMiddleLinkedList()
                    9 -> addingEndLinkedList()
                    10 -> searchByValueLinkedList()
                    11 -> removingBeginningLinkedList()
                    12 -> removingMiddleLinkedList()
                    13 -> removingEndLinkedList()
                    14 -> addingBeginningCopyOnWriteArrayList()
                    15 -> addingMiddleCopyOnWriteArrayList()
                    16 -> addingEndCopyOnWriteArrayList()
                    17 -> searchByValueCopyOnWriteArrayList()
                    18 -> removingBeginningCopyOnWriteArrayList()
                    19 -> removingMiddleCopyOnWriteArrayList()
                    20 -> removingEndCopyOnWriteArrayList()
                }
            }
            ++position
        }
        _collection = null
    }

    private fun createList(collectionSize: Int): List<Int> {
        return List(collectionSize) { 0 }
    }

    private fun addingBeginningArrayList() {
        var array: ArrayList<Int>? = collection.toCollection(arrayListOf())
        repeat (elementsAmount) {
            array!!.add(0, 1)
        }
        array = null
    }

    private fun addingMiddleArrayList() {
        var array: ArrayList<Int>? = collection.toCollection(arrayListOf())
        repeat (elementsAmount) {
            array!!.add(array!!.size / 2, 1)
        }
        array = null
    }

    private fun addingEndArrayList() {
        var array: ArrayList<Int>? = collection.toCollection(arrayListOf())
        repeat (elementsAmount) {
            array!!.add(array!!.lastIndex, 1)
        }
        array = null
    }

    private fun searchByValueArrayList() {
        var array: ArrayList<Int>? = collection.toCollection(arrayListOf())
        repeat (elementsAmount) {
            array!!.binarySearch(0, 0, array!!.lastIndex)
        }
        array = null
    }

    private fun removingBeginningArrayList() {
        var array: ArrayList<Int>? = collection.toCollection(arrayListOf())
        repeat (elementsAmount) {
            array!!.removeAt(0)
        }
        array = null
    }

    private fun removingMiddleArrayList() {
        var array: ArrayList<Int>? = collection.toCollection(arrayListOf())
        repeat (elementsAmount) {
            array!!.removeAt(array!!.size / 2)
        }
        array = null
    }

    private fun removingEndArrayList() {
        var array: ArrayList<Int>? = collection.toCollection(arrayListOf())
        repeat (elementsAmount) {
            array!!.removeAt(array!!.lastIndex)
        }
        array = null
    }

    private fun addingBeginningLinkedList() {
        var array: LinkedList<Int>? = collection.toCollection(LinkedList())
        repeat (elementsAmount) {
            array!!.add(0, 1)
        }
        array = null
    }

    private fun addingMiddleLinkedList() {
        var array: LinkedList<Int>? = collection.toCollection(LinkedList())
        repeat (elementsAmount) {
            array!!.add(array!!.size / 2, 1)
        }
        array = null
    }

    private fun addingEndLinkedList() {
        var array: LinkedList<Int>? = collection.toCollection(LinkedList())
        repeat (elementsAmount) {
            array!!.add(array!!.lastIndex, 1)
        }
        array = null
    }

    private fun searchByValueLinkedList() {
        var array: LinkedList<Int>? = collection.toCollection(LinkedList())
        repeat (elementsAmount) {
            array!!.binarySearch(0, 0, array!!.lastIndex)
        }
        array = null
    }

    private fun removingBeginningLinkedList() {
        var array: LinkedList<Int>? = collection.toCollection(LinkedList())
        repeat (elementsAmount) {
            array!!.removeAt(0)
        }
        array = null
    }

    private fun removingMiddleLinkedList() {
        var array: LinkedList<Int>? = collection.toCollection(LinkedList())
        repeat (elementsAmount) {
            array!!.removeAt(array!!.size / 2)
        }
        array = null
    }

    private fun removingEndLinkedList() {
        var array: LinkedList<Int>? = collection.toCollection(LinkedList())
        repeat (elementsAmount) {
            array!!.removeAt(array!!.lastIndex)
        }
        array = null
    }

    private fun addingBeginningCopyOnWriteArrayList() {
        var array: CopyOnWriteArrayList<Int>? = collection.toCollection(CopyOnWriteArrayList())
        repeat (elementsAmount) {
            array!!.add(0, 1)
        }
        array = null
    }

    private fun addingMiddleCopyOnWriteArrayList() {
        var array: CopyOnWriteArrayList<Int>? = collection.toCollection(CopyOnWriteArrayList())
        repeat (elementsAmount) {
            array!!.add(array!!.size / 2, 1)
        }
        array = null
    }

    private fun addingEndCopyOnWriteArrayList() {
        var array: CopyOnWriteArrayList<Int>? = collection.toCollection(CopyOnWriteArrayList())
        repeat (elementsAmount) {
            array!!.add(array!!.lastIndex, 1)
        }
        array = null
    }

    private fun searchByValueCopyOnWriteArrayList() {
        var array: CopyOnWriteArrayList<Int>? = collection.toCollection(CopyOnWriteArrayList())
        repeat (elementsAmount) {
            array!!.binarySearch(0, 0, array!!.lastIndex)
        }
        array = null
    }

    private fun removingBeginningCopyOnWriteArrayList() {
        var array: CopyOnWriteArrayList<Int>? = collection.toCollection(CopyOnWriteArrayList())
        repeat (elementsAmount) {
            array!!.removeAt(0)
        }
        array = null
    }

    private fun removingMiddleCopyOnWriteArrayList() {
        var array: CopyOnWriteArrayList<Int>? = collection.toCollection(CopyOnWriteArrayList())
        repeat (elementsAmount) {
            array!!.removeAt(array!!.size / 2)
        }
        array = null
    }

    private fun removingEndCopyOnWriteArrayList() {
        var array: CopyOnWriteArrayList<Int>? = collection.toCollection(CopyOnWriteArrayList())
        repeat (elementsAmount) {
            array!!.removeAt(array!!.lastIndex)
        }
        array = null
    }

}