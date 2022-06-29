package com.base.library.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newCoroutineContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * 自己的launch扩展,安全的调用协程
 */
@OptIn(ExperimentalCoroutinesApi::class)
fun <Result> CoroutineScope.launchSafety(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Result, // 这里返回 Result 泛型是为了回掉方便使用
): SafetyCoroutine<Result> {
    val newContext = newCoroutineContext(context)
    val coroutine = SafetyCoroutine<Result>(newContext)
    coroutine.start(start, coroutine, block)
    return coroutine
}
