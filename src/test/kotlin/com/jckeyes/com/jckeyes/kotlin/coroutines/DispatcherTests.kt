@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

package com.jckeyes.com.jckeyes.kotlin.coroutines

import com.jckeyes.com.jckeyes.kotlin.common.TestOutputFormatter
import com.jckeyes.com.jckeyes.kotlin.common.log
import kotlinx.coroutines.experimental.CoroutineDispatcher
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.concurrent.Executors
import kotlin.coroutines.experimental.CoroutineContext

@ExtendWith(TestOutputFormatter::class)
class DispatcherTests {

    object CustomPool : CoroutineDispatcher() {
        private val executor = Executors.newSingleThreadExecutor {
            Thread(it, "💻").apply { isDaemon = true }
        }

        override fun dispatch(context: CoroutineContext, block: Runnable)
                = executor.execute(block)
    }

    @Test
    fun `using custom dispatcher`() = runBlocking(CustomPool) {
        log("😃")
    }
}

