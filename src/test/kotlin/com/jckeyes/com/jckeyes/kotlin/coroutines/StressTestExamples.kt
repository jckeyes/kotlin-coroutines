@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

package com.jckeyes.com.jckeyes.kotlin.coroutines

import com.jckeyes.com.jckeyes.kotlin.common.TestOutputFormatter
import com.jckeyes.com.jckeyes.kotlin.common.log
import kotlinx.coroutines.experimental.CoroutineDispatcher
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.lang.Thread.sleep
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit.SECONDS
import kotlin.coroutines.experimental.CoroutineContext


@ExtendWith(TestOutputFormatter::class)
class StressTestExamples {

    // Threads are not well suited for short, highly concurrent
    // tasks. To prove it, lets try 100 simple operations
    // with a very small thread pool.
    @Test
    fun `threads are slow`() {
        (1..100).forEach {
            limitedPoolExecutor.execute {
                sleep(1000)
                log("🐌")
            }
        }

        limitedPoolExecutor.awaitTermination(5, SECONDS)
    }



    // Coroutines can do this work much, much faster. Delay
    // is a suspending function, which means while one coroutine
    // waits, we can move on to process the other.
    // We can complete orders of magnitude more tasks because of this.
    @Test
    fun `coroutines are fast`() = runBlocking {
        (1..10000).map {
            launch(LimitedPool) {
                delay(1000)
                log("🏎️")
            }
        }.forEach { it.join() }
    }

}




// ... a very tiny thread pool
private val limitedPoolExecutor = Executors.newFixedThreadPool(2) {
    Thread(it).apply { isDaemon = false }
}



// This is a coroutine dispatcher. If you need fine grain control over
// how and where your coroutines are dispatched, there are a number of
// options (like this) that you can use.
object LimitedPool : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        limitedPoolExecutor.execute(block)
    }
}



