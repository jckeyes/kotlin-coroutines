package com.jckeyes.com.jckeyes.kotlin.common

import java.util.*

fun Int.asRandom() = rand.nextInt(this)

fun <T> List<T>.getRandom() = this[this.size.asRandom()]

fun log(a: String, b: String, sum: String) = println("$a + $b = $sum")
fun log(message: String) = println("$message : $thread")
fun logCollection(items: Collection<Any>) = items.groupingBy { it }.eachCount().forEach {
    println("${it.key} x ${it.value}")
}

private val rand = Random()
private val thread: String get() = Thread.currentThread().name

