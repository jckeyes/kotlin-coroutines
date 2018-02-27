@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

package com.jckeyes.com.jckeyes.kotlin.coroutines

import com.jckeyes.com.jckeyes.kotlin.common.TestOutputFormatter
import com.jckeyes.kotlin.coroutines.log
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.lang.Thread.sleep
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

@ExtendWith(TestOutputFormatter::class)
class StressTests {

    @Test
    fun `threads are slow`() {
        val items = aLotOfNumbers.map {
            CompletableFuture.supplyAsync {
                sleep(aLittleBit)
                "\uD83D\uDC0C"
            }
        }

        log(items.map { it.get() })
    }

    @Test
    fun `coroutines are fast`() = runBlocking {
        val items = aLotOfNumbers.map {
            async {
                delay(aLittleBit)
                "\uD83C\uDFCE️"
            }
        }

        log(items.map { it.await() })
    }


    private val aLotOfNumbers = (1..10000)
    private val aLittleBit = 1000L
}
