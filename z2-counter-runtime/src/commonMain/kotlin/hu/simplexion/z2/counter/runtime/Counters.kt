/*
 * Copyright © 2023, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
@file:Suppress("unused")

package hu.simplexion.z2.counter.runtime

/**
 * Stores how many times a given code ran. Keys of the map identify the code point, values
 * show how many times that specific code point has run.
 */
object Counters {

    val counters = mutableMapOf<String, Long>()

    fun increment(fileName: String, functionName: String, line: Int, offset: Int) {
        val key = "$fileName:$functionName:$line:$offset"
        val current = counters[key] ?: 0
        counters[key] = current + 1
    }

    override fun toString(): String =
        counters.map { "${it.key} ${it.value}" }.joinToString("\n")

}