@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

package com.jckeyes.com.jckeyes.kotlin.coroutines

import com.jckeyes.com.jckeyes.kotlin.common.TestOutputFormatter
import com.jckeyes.com.jckeyes.kotlin.common.log
import com.jckeyes.com.jckeyes.kotlin.common.logCollection
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.run
import kotlinx.coroutines.experimental.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.concurrent.thread

@ExtendWith(TestOutputFormatter::class)
class BlockingExamples {

    // Normal, blocking code
    @Test
    fun `normal blocking code`() {
        connect()
        mine()
        disconnect()
    }



    // We can use another thread to unblock,
    // but we have to block to join
    @Test
    fun `non-blocking thread`() {
        connect()
        val th = thread { mine() }
        disconnect()
        th.join()
    }



    // launch will perform the task in another
    // thread. Then, we can start another coroutine
    // and suspend until we're able to join it
    @Test
    fun `non-blocking coroutine`() {
        connect()
        val job = launch { mine() }
        disconnect()

        runBlocking {
            job.join()
        }
    }



    // Sequential code is hard to write using threads.
    @Test
    fun `blocking thread`() {
        connect()
        thread { mine() }.join()
        disconnect()
    }



    // runBlocking starts a coroutine on the current
    // thread. As the name suggests, it will block
    // until it is complete
    @Test
    fun `blocking coroutine`() {
        connect()
        runBlocking { mine() }
        disconnect()
    }



    // If we combine runBlocking with run, we can
    // write sequential code that doesn't block
    @Test
    fun `better sequential code`() = runBlocking {
        connect()
        val coins = run(CommonPool) { mineAll() }
        disconnect()
        profit(coins)
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
