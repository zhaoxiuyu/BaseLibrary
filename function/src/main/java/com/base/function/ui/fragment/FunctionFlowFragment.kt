package com.base.function.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.base.function.databinding.FunctionFragmentFlowBinding
import com.base.library.mvvm.VMFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * 异步流
 */
class FunctionFlowFragment : VMFragment<FunctionFragmentFlowBinding>() {

    override fun initArgs(mArguments: Bundle?) {
    }

    override fun getRootView(): View = viewBinding.root

    override fun initData(savedInstanceState: Bundle?) {
        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().navigateUp()
        }
        viewBinding.but1.setOnClickListener {
            guaqi()
        }
    }

    override fun registerObserve() {
    }

    // 创建Flow
    private suspend fun flow1() {
        flow {
            (1..5).forEach {
                emit(it)
            }
        }.collect {
            // collect 和 RxJava的subscribe一样，用来消费数据的，collect是挂起函数
            print("$it")
        }

        print("------------------")

        // flowOf帮助可变数组生成Flow实例,其实和上面的代码一样，分别emit发送值
        flowOf(1, 2, 3, 4, 5).collect { print("$it") }

        print("------------------")

        // 创建从范围生成值的流
        (1..5).asFlow().collect { print("$it") }
    }

    // 切换线程
    private suspend fun flow2() {
        flow {
            for (i in 1..2) {
                delay(100)
                print("发送之前 $i")
                emit(i)
            }
        }.map {
            print("map 1")
            it * it
        }
            // flowOn 之前的操作在 flowOn指定的线程之内,flowOn之后的操作符运行在整个Flow运行的CoroutineContext内
            .flowOn(Dispatchers.IO)
            .map {
                print("map 2")
                "$it"
            }
            .flowOn(Dispatchers.Default)
            .map {
                print("map 3")
                it.toInt()
            }
            .flowOn(Dispatchers.Main)
            .map {
                print("map 4")
                "$it"
            }
            .collect { print(it) }
    }

    // 操作符-transform
    private suspend fun flowTransform() {
        (1..5).asFlow().transform {
            // 使用transform可以任意多次调用emit
            emit(1 + it)
            delay(100)
            emit(11 + it)
        }.collect { print("$it") }
    }

    // 操作符-take
    private suspend fun flowTake() {
        // take只取前几个emit发射
        (1..5).asFlow().take(2).collect { print("$it") }
    }

    // 操作符-reduce
    private suspend fun flowReduce() {
        // reduce两个元素操作之后拿到的值跟后面的元素进行操作,用于把flow 简化合并为一个值
        val reduce = (1..5).asFlow().reduce { a, b ->
            print("$a , $b")
            a + b
        }
        print("reduce = $reduce")
    }

    // 操作符-fold
    private suspend fun flowFold() {
        // 从初始值'3'开始累加,附加到每个元素上面,下面这个例子:3+1+2+3+4+5=18
        val fold = (1..5).asFlow().fold(3, { a, b ->
            a + b
        })
        print("fold = $fold")
    }

    // 操作符-zip
    private suspend fun flowZip() {
        val flowA = (1..5).asFlow()
        val flowB = flowOf("A", "B", "C", "D", "E", "F")
            .onEach { delay(100) } // 每100毫秒发射一次

        // 组合两个流中相关的值,新的item个数等于 较小的flow的个数
        flowA.zip(flowB) { a, b ->
            "$a + $b"
        }.collect { print(it) }
    }

    // 操作符
    // flattenMerge,flattenConcat,并发收集所有传入的流,将它们的值合并到一个单独的流,然后发射
    private suspend fun flowFlattenMerge() {
        val flowA = (1..5).asFlow()
        val flowB = flowOf("A", "B", "C", "D", "E", "F")
        val flowC = flowOf("a", "b", "c", "d", "e", "f")
        flowOf(flowA, flowB, flowC).flattenMerge(2).collect {
            print("$it")
        }
        print("----------------------------")
        flowOf(flowA, flowB, flowC).flattenConcat().collect { print("$it") }
    }

    // 操作符-flatMapConcat
    // collect函数在收集新值之前会等待flatMapConcat内部的Flow完成
    private suspend fun flowFlatMapConcat() {
        (1..5).asFlow()
            .onStart { print("onStart") }
            .onEach { delay(100) } // 每100毫秒发射一个数字
            .flatMapConcat {
                flow {
                    emit("$it k")
                    delay(500)
                    emit("$it J")
                }
            }
            .collect { print(it) }
    }

    private suspend fun flowMap() {
        flowLoadData()
            .map { "$it" } // map 将最终结果映射为其他类型
            .filter { it == "1" } // filter 对结果集添加过滤条件
            .collect { print(it) }
    }

    private fun flowLoadData() = flow {
        for (i in 1..3) {
            emit(i)
        }
    }

    private fun guaqi() {
        lifecycleScope.launch {
            flow2()
        }
    }

    private fun print(msg: String) {
        Log.d("FlowActivity", "thread = ${Thread.currentThread().name} , msg = $msg")
    }

}