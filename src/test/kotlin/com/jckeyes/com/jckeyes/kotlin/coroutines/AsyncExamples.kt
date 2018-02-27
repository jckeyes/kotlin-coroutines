@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

package com.jckeyes.com.jckeyes.kotlin.coroutines

import com.jckeyes.com.jckeyes.kotlin.common.TestOutputFormatter
import com.jckeyes.com.jckeyes.kotlin.common.getRandom
import com.jckeyes.com.jckeyes.kotlin.common.log
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import kotlinx.coroutines.experimental.run
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.io.File
import java.lang.Thread.sleep
import java.util.concurrent.CompletableFuture
import kotlin.concurrent.thread

@ExtendWith(TestOutputFormatter::class)
class AsyncExamples {

    // Here we have a number of expensive
    // calls, that are done sequentially.
    // It's slow.
    @Test
    fun `blocking calls`() {
        val a = randomoji()
        val b = randomoji()
        val sum = randomoji()

        log(a, b, sum)
    }



    // You could solve this problem with threads,
    // but please, don't do that.
    @Test
    fun `async threads`() {
        var a: String? = null
        var b: String? = null
        var sum: String? = null

        listOf(
                thread { a = randomoji() },
                thread { b = randomoji() },
                thread { sum = randomoji() }
        ).forEach { it.join() }

        log(a!!, b!!, sum!!)
    }



    // A better approach, would be to use Futures
    // and Suppliers. Again, the problem with this is that
    // we will inevitably have to block in order to get our
    // results
    @Test
    fun `async supplier`() {
        val a = CompletableFuture.supplyAsync { randomoji() }
        val b = CompletableFuture.supplyAsync { randomoji() }
        val sum = CompletableFuture.supplyAsync { randomoji() }

        log(a.get(), b.get(), sum.get())
    }



    // We can avoid this problem using the async coroutine
    // builder. It returns a Deferred object that has a
    // suspending await function
    @Test
    fun `async coroutine`() = runBlocking {
        val a = async { randomoji() }
        val b = async { randomoji() }
        val sum = async { randomoji() }

        log(a.await(), b.await(), sum.await())
    }





    // All the kids are talking about it
    private fun randomoji(): String {
        val emojiPath = this.javaClass.getResource("/emojis.txt").path
        val emoji = File(emojiPath).readText().split("\n").getRandom()

        sleep(1000)
        log(emoji)

        return emoji
    }
}