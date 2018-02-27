@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

package com.jckeyes.com.jckeyes.kotlin.coroutines

import com.jckeyes.com.jckeyes.kotlin.common.TestOutputFormatter
import com.jckeyes.com.jckeyes.kotlin.common.log
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.coroutines.experimental.buildSequence

@ExtendWith(TestOutputFormatter::class)
class SequenceBuilderExamples {

    // We can use coroutine to create sequence builders.
    // In this case, it's an infinite sequence.
    //
    // buildSequence uses the @RestrictSuspension annotation,
    // which means that we are not able to suspend it any further.
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