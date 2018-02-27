@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

package com.jckeyes.com.jckeyes.kotlin.coroutines

import com.jckeyes.com.jckeyes.kotlin.common.TestOutputFormatter
import com.jckeyes.com.jckeyes.kotlin.common.getRandom
import com.jckeyes.com.jckeyes.kotlin.common.log
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.io.File
import java.lang.Thread.sleep
import java.util.concurrent.CompletableFuture
import kotlin.concurrent.thread

@ExtendWith(TestOutputFormatter::class)
class AsyncTests {

    @Test
    fun `blocking calls`() {
        val a = randomoji()
        val b = randomoji()
        val sum = randomoji()

        log(a, b, sum)
    }

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

    @Test
    fun `async supplier`() {
        val a = CompletableFuture.supplyAsync { randomoji() }
        val b = CompletableFuture.supplyAsync { randomoji() }
        val sum = CompletableFuture.supplyAsync { randomoji() }

        log(a.get(), b.get(), sum.get())
    }

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