@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

package com.jckeyes.com.jckeyes.kotlin.coroutines

import com.jckeyes.com.jckeyes.kotlin.common.CommonPoolWatcher
import com.jckeyes.com.jckeyes.kotlin.common.TestOutputFormatter
import com.jckeyes.com.jckeyes.kotlin.common.log
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.concurrent.thread

@ExtendWith(value = [CommonPoolWatcher::class, TestOutputFormatter::class])
class BlockingTests {

    /**
     * Run of the mill, blocking code.
     * Nothing to see here.
     */
    @Test
    fun `normal blocking code`() {
        start()
        mineBits()
        end()
    }

    /**
     * If we don't want to tie up the main thread
     * while we mine our bits, we can do the work
     * in a thread. Kotlin's [thread] method is a
     * great way to do that.
     */
    @Test
    fun `non-blocking thread`() {
        start()
        val th = thread { mineBits() }
        end()
        th.join()
    }

    @Test
    fun `non-blocking coroutine`() {
        start()
        val job = launch { mineBits() }
        end()

        runBlocking {
            job.join()
        }
    }

    @Test
    fun `blocking thread`() {
        start()
        thread { mineBits() }.join()
        end()
    }

    @Test
    fun `blocking coroutine`() {
        start()
        runBlocking { mineBits() }
        end()
    }

    @Test
    fun `blocking coroutine with context`() {
        start()
        runBlocking(CommonPool) { mineBits() }
        end()
    }


    // Rockets launch, just like coroutines
    private fun start() {
        log("🚀")
    }

    // The universal sign for finish
    private fun end() {
        log("🏁")
    }

    // The culmination of years of work by dozens
    // of PhD students
    private fun mineBits() {
        log((0..10000000).map { "🔨" }.last())
    }
}
