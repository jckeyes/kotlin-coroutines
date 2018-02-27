@file:Suppress("EXPERIMENTAL_FEATURE_WARNING", "MemberVisibilityCanBePrivate")

package com.jckeyes.com.jckeyes.kotlin.coroutines

import com.jckeyes.com.jckeyes.kotlin.common.TestOutputFormatter
import com.jckeyes.com.jckeyes.kotlin.common.log
import kotlinx.coroutines.experimental.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.lang.Thread.sleep
import java.util.concurrent.CompletableFuture.supplyAsync
import java.util.concurrent.Future
import kotlin.coroutines.experimental.suspendCoroutine

@ExtendWith(TestOutputFormatter::class)
class SuspendFunctionExamples {


    // What if you're using a library that's already
    // implemented using a different async library?
    // (e.g. Futures)
    class Gourmet {
        fun order(): Future<String> = supplyAsync {
            sleep(1000)
            "🌭"
        }
    }


    // We can prevent writing blocking code by creating
    // a suspending function that waits for the completion
    // of the call
    suspend fun awaitOrder(g: Gourmet) = suspendCoroutine<String> {
        it.resume(g.order().get())
    }

    @Test
    fun `suspending function`() = runBlocking {
        val meal = awaitOrder(gourmet)
        log(meal)
    }



    // Even better, we can create an suspending extension function
    // directly off of the Future class
    suspend fun <T> Future<T>.await() = suspendCoroutine<T> {
        it.resume(this.get())
    }

    @Test
    fun `suspending extension function`() = runBlocking {
        val meal = gourmet.order().await()
        log(meal)
    }



    // Not all suspending functions will suspend the execution
    // of a coroutine. This method, for example, will not
    // suspend as the value is already available.
    //
    // In fact, IntelliJ is smart enough to tell you that it
    // doesn't need to be a suspending function.
    suspend fun breakfast() = "🍩"

    @Test
    fun `non-suspending suspend function`() = runBlocking {
        val meal = breakfast()
        log(meal)
    }

    // only the best
    private val gourmet = Gourmet()
}


