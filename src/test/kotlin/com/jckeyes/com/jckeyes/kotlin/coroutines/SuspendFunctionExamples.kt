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


    /**
     * Have a class that's implemented using [Future] or
     * some other async library? You can still leverage
     * the suspending power of coroutines.
     */
    class Gourmet {
        fun order(): Future<String> = supplyAsync {
            sleep(1000)
            "🌭"
        }
    }



    /**
     * As the name suggests, suspend functions have the potential
     * to suspend the execution of a coroutine. Because of this,
     * they can only be called from a coroutine or another suspend
     * function. Here, we're using [suspendCoroutine] to resume
     * when [Future.get] completes.
     */
    suspend fun <T> awaitResult(future: Future<T>) = suspendCoroutine<T> {
        it.resume(future.get())
    }

    @Test
    fun `suspending function`() = runBlocking {
        val future = gourmet.order()
        val meal = awaitResult(future)
        log(meal)
    }



    /**
     * Even better, we can use extension functions to create an
     * await method on [Future].
     */
    suspend fun <T> Future<T>.await() = suspendCoroutine<T> {
        it.resume(this.get())
    }

    @Test
    fun `suspending extension function`() = runBlocking {
        val meal = gourmet.order().await()
        log(meal)
    }


    /**
     * Not all suspending functions will suspend the execution
     * of a coroutine. This method, for example, will not
     * suspend as the value is already available.
     *
     * In fact, IntelliJ is smart enough to tell you that it
     * doesn't need to be a suspending function.
     */
    suspend fun breakfast() = "🍩"

    @Test
    fun `non-suspending suspend function`() = runBlocking {
        val meal = breakfast()
        log(meal)
    }

    // only the best
    private val gourmet = Gourmet()
}


