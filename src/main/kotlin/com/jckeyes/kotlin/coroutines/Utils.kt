@file:JvmName("Utils")

package com.jckeyes.kotlin.coroutines

private val thread: String
    get() = Thread.currentThread().name

fun log(message: String) {
    println("$message : $thread")
}

fun log(items: Collection<Any>) {
    items.groupingBy { it }.eachCount().forEach {
        println("${it.key} x ${it.value}")
    }
}

fun log(a: String, b: String, sum: String) {
    println("$a + $b = $sum")
}


const val adjectivePath = "/Users/john.keyes/Development/Sandbox/kotlin-coroutines-introduction/words/adjectives.txt"
const val adverbPath = "/Users/john.keyes/Development/Sandbox/kotlin-coroutines-introduction/words/adverbs.txt"
const val nounPath = "/Users/john.keyes/Development/Sandbox/kotlin-coroutines-introduction/words/nouns.txt"
const val emojiPath = "/Users/john.keyes/Development/Sandbox/kotlin-coroutines-introduction/words/emojis.txt"
const val a = "A"
const val an = "AN"
val nouns = listOf('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U')
