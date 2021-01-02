package com.base.app.module.test

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() {

    test()
    Thread.sleep(1000)
    println("协程执行结束.....")

}

fun test() = runBlocking {
    println("1")
    val job = async {
        println("2")
        delay(500)
        println("4... ${Thread.currentThread().name}")
        return@async "hello"
    }
    println("3")
    println(job.await())
    println("5")
}
