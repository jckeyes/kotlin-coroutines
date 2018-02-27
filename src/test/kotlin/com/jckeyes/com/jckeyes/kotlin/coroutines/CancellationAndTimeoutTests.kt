@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

package com.jckeyes.com.jckeyes.kotlin.coroutines

import com.jckeyes.com.jckeyes.kotlin.common.TestOutputFormatter
import com.jckeyes.com.jckeyes.kotlin.common.log
import kotlinx.coroutines.experimental.cancelAndJoin
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.lang.Thread.sleep

@ExtendWith(TestOutputFormatter::class)
class CancellationAndTimeoutTests {

    @Test
    fun `coroutines are like daemon threads`() {
        launch {
            repeat(1000) { eat() }
        }

        cleanPlate()
        sleep(1000)
        cleanPlate()
    }

    @Test
    fun `coroutines can be cancelled`() = runBlocking {
        val job = launch {
            while (isActive) { eat() }
        }

        cleanPlate()
        delay(1000)
        job.cancelAndJoin()
        cleanPlate()
    }

    // Drooly face emoji
    private fun eat() {
        log((0..10000000).map { "*" }.reduce { _, _ -> "🌮" })
    }

    // See, mom? All done.
    private fun cleanPlate() {
        log("🍽")
    }

}