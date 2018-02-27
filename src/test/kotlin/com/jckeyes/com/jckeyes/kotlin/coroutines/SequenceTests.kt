@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

package com.jckeyes.com.jckeyes.kotlin.coroutines

import com.jckeyes.com.jckeyes.kotlin.common.TestOutputFormatter
import com.jckeyes.com.jckeyes.kotlin.common.log
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.coroutines.experimental.buildSequence

@ExtendWith(TestOutputFormatter::class)
class SequenceTests {

    private val quackers = buildSequence {
        var result = ""
        while (true) {
            result += "🦆"
            yield(result)
        }
    }

    @Test
    fun `take from a sequence`() {
        quackers.take(6)
                .forEach { println(it) }

        quackers.takeWhile { it.length <= 10 }
                .toList()
                .reversed()
                .forEach { println(it) }
    }
}