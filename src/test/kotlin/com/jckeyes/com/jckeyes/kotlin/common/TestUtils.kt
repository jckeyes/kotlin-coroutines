package com.jckeyes.com.jckeyes.kotlin.common

import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import java.util.*
import java.util.concurrent.ForkJoinPool

class CommonPoolWatcher : AfterEachCallback {
    override fun afterEach(context: ExtensionContext?) {
        while (ForkJoinPool.commonPool().activeThreadCount > 0) {
        }
    }
}

class TestOutputFormatter : BeforeEachCallback, AfterEachCallback {

    private var testStartTime = System.currentTimeMillis()

    override fun afterEach(context: ExtensionContext?) {
        val runtime = System.currentTimeMillis() - testStartTime
        println("──────────────────────────────────────────")
        println("$runtime ms\n")
    }

    override fun beforeEach(context: ExtensionContext?) {
        testStartTime = System.currentTimeMillis()
        println(context?.formatForTitle())
        println("──────────────────────────────────────────")
    }

    private fun ExtensionContext.formatForTitle() = this.displayName.trim('(', ')').toUpperCase()
}

private val rand = Random()
fun Int.asRandom() = rand.nextInt(this)

fun <T> List<T>.getRandom() = this[this.size.asRandom()]

fun Int.asRange(start: Int = 1) = (start..this)
