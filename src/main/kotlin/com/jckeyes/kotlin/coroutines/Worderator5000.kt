@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

package com.jckeyes.kotlin.coroutines

import java.io.File
import java.util.*

class Worderator5000 {

    private val rand = Random()

    fun noun() = randomWordFromFile(nounPath)
    fun emoji() = randomWordFromFile(emojiPath)
    fun adjective() = randomWordFromFile(adjectivePath)
    fun adverb() = randomWordFromFile(adverbPath).let { "${articleFor(it)} $it" }

    private fun articleFor(word: String) = if (word.first() in nouns) an else a

    private fun randomWordFromFile(path: String) = File(path).let {
        val words = it.readText().split("\n")
        log("finding word in ${it.name}")
        words[rand.nextInt(words.size)]
    }
}


