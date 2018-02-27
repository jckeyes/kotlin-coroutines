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
class SuspendingTests {

    suspend fun <T> awaitResult(future: Future<T>) = suspendCoroutine<T> {
        it.resume(future.get())
    }

    @Test
    fun `suspending function`() = runBlocking {
        val future = gourmet.order()
        val meal = awaitResult(future)
        log(meal)
    }


    suspend fun <T> Future<T>.await() = suspendCoroutine<T> {
        it.resume(this.get())
    }

    @Test
    fun `suspending extension function`() = runBlocking {
        val meal = gourmet.order().await()
        log(meal)
    }

    private val gourmet = Gourmet()
}


class Gourmet {

    fun order(): Future<String> = supplyAsync {
        sleep(1000)
        "🌭"
    }

}
