@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

package com.jckeyes.com.jckeyes.kotlin.coroutines

import com.jckeyes.com.jckeyes.kotlin.common.CommonPoolWatcher
import com.jckeyes.com.jckeyes.kotlin.common.TestOutputFormatter
import com.jckeyes.com.jckeyes.kotlin.common.log
import com.jckeyes.com.jckeyes.kotlin.common.logCollection
import kotlinx.coroutines.experimental.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.concurrent.ForkJoinPool
import kotlin.concurrent.thread

@ExtendWith(value = [CommonPoolWatcher::class, TestOutputFormatter::class])
class BlockingTests {

    /**
     * Run of the mill, blocking code.
     * Nothing to see here.
     */
    @Test
    fun `normal blocking code`() {
        connect()
        mine()
        disconnect()
    }

    /**
     * We can unblock the main thread by creating
     * a new [thread]. But, in reality, we still
     * have to block as soon as we [Thread.join]
     */
    @Test
    fun `non-blocking thread`() {
        connect()
        val th = thread { mine() }
        disconnect()
        th.join()
    }


    /**
     * The [launch] coroutine is very similar to the
     * above example. In this case, however, the work
     * is dispatched to the [ForkJoinPool] (by default)
     * and [Job.join] can suspend execution until it is
     * complete. That means, we do not have to block
     * the main thread while we wait for our worker to
     * complete.
     */
    @Test
    fun `non-blocking coroutine`() {
        connect()
        val job = launch { mine() }
        disconnect()

        runBlocking {
            job.join()
        }
    }


    /**
     * Sequential code is hard to write using threads.
     * Even though we're doing [mine] in another
     * thread here, we're still blocking with [Thread.join]
     */
    @Test
    fun `blocking thread`() {
        connect()
        thread { mine() }.join()
        disconnect()
    }

    /**
     * We can use the [runBlocking] to start a coroutine
     * on the current thread. This allows us to suspend
     * that execution with any number of suspending functions.
     *
     * As the name suggests, this code is still blocking
     * while we do work.
     */
    @Test
    fun `blocking coroutine`() {
        connect()
        runBlocking { mine() }
        disconnect()
    }


    /**
     * Here we have sequential code that doesn't
     * block the main thread. Because [run] is a
     * suspending function, it can suspend the execution
     * of [runBlocking] until it is complete.
     */
    @Test
    fun `better sequential code`() = runBlocking {
        connect()
        val blocks = run(CommonPool) { mineAll() }
        disconnect()
        profit(blocks)
    }


    // Rockets launch, just like coroutines
    private fun connect() {
        log("🚀")
    }

    // The universal sign for finish
    private fun disconnect() {
        log("🏁")
    }

    // $$$$
    private fun profit(blocks: List<String>) {
        logCollection(blocks)
    }

    // The culmination of years of work by dozens
    // of PhD students
    private fun mineAll() = (1..10).map { mine() }
    private fun mine(): String {
        val block = (0..10000000).map { "🔨" }.last()
        log(block)
        return block
    }
}
