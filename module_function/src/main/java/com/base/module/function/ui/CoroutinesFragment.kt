package com.base.module.function.ui

import android.os.Bundle
import androidx.activity.addCallback
import androidx.lifecycle.rxLifeScope
import androidx.navigation.fragment.findNavController
import com.base.library.base.BFragment
import com.base.module.function.databinding.FragmentCoroutinesBinding
import com.rxlife.coroutine.RxLifeScope
import kotlinx.coroutines.*

/**
 * 协程就是切换线程
 * 挂起就是可以自动切换回来的切线程
 * 挂起的非阻塞式指的是它能用看起来阻塞的代码写出非阻塞的操作
 */
class CoroutinesFragment : BFragment() {

    private val viewBinding by lazy { FragmentCoroutinesBinding.inflate(layoutInflater) }

    private val sb = StringBuilder()

    override fun initArgs(mArguments: Bundle?) {
    }

    override fun initView() {
        setContentView(viewBinding.root, topPadding = viewBinding.ll)
//        setTitleBarOperation("协程-练手", object : MyTitleBarListener() {
//            override fun onLeftClick(v: View?) {
//                findNavController().navigateUp()
//            }
//        })
        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().navigateUp()
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        viewBinding.tvLoadImg.setOnClickListener {
            loadImg()
        }
        viewBinding.tvLoadImg2.setOnClickListener {
            loadImg2()
        }
        viewBinding.tvLoadImg3.setOnClickListener {
            loadImg3()
        }
    }

    override fun registerObserve() {
    }

    // 协程内按顺序执行,多个withContext是串行的,可直接返回耗时任务结果
    private fun loadImg() {
        rxLifeScope.launch({
            // 协程体
            sb.delete(0, sb.length)

            sb.append("1 ${Thread.currentThread().name} \n")

            val image = withContext(Dispatchers.IO) {
                sb.append("2 ${Thread.currentThread().name} \n")

                repeat(100000) {

                }

                sb.append("3 ${Thread.currentThread().name} \n")
            }

            sb.append("4 ${Thread.currentThread().name} \n")

            val image2 = getLoadImage()

            sb.append("7 ${Thread.currentThread().name} \n")

            val image3 = getLoadImageDone()

            sb.append("666 ${Thread.currentThread().name} \n")

            viewBinding.tvContent.text = sb.toString()
        }, {
            // 异常回调
        }, {
            // 协程开始回调
        }, {
            // 协程结束回调，不管成功/失败都会回调
        })
    }

    /**
     * 自定义 一个 挂起函数
     * 加上 suspend 关键字, withContext 把函数包裹住就可以了
     */
    private suspend fun getLoadImage() = withContext(Dispatchers.IO) {
        sb.append("5 ${Thread.currentThread().name} \n")

        repeat(100000) {

        }

        sb.append("6 ${Thread.currentThread().name} \n")
    }

    /**
     * 自定义 一个 挂起函数
     * 直接或间接的调用自带的挂起函数，delay 也是挂起函数
     */
    private suspend fun getLoadImageDone() {
        sb.append("8 ${Thread.currentThread().name} \n")

        delay(500)

        sb.append("9 ${Thread.currentThread().name} \n")
    }

    // async 开启一个子协程,后面代码继续执行,多个async 是并行的，调用 await 返回的是 Deferred 获取结果
    // await 在 async 未执行完成返回结果时才会挂起协程，否则直接获取结果赋值变量不会挂起
    // await 是挂起函数，使用await就和withContext一样，挂起函数执行完才会执行下一个async
    private fun loadImg2() {
        rxLifeScope.launch({
            // 协程体
            sb.delete(0, sb.length)

            sb.append("1 ${Thread.currentThread().name} \n")

            val image1: Deferred<StringBuilder> = async {
                sb.append("2 ${Thread.currentThread().name} \n")
                repeat(100000) {}
                sb.append("3 十万次循环执行完了 ${Thread.currentThread().name} \n")
            }

            sb.append("4 ${Thread.currentThread().name} \n")

            val image2 = async {
                sb.append("5 ${Thread.currentThread().name} \n")
                repeat(100000) {}
                sb.append("6 十万次循环执行完了 ${Thread.currentThread().name} \n")
            }.await()

            sb.append("7 ${Thread.currentThread().name} \n")

            image1.await()

            sb.append("666 ${Thread.currentThread().name} \n")

            viewBinding.tvContent.text = sb.toString()
        }, {
            // 异常回调
        }, {
            // 协程开始回调
        }, {
            // 协程结束回调，不管成功/失败都会回调
        })
    }

    /**
     * launch 和 async 是一样的，都是启动协程（切线程），在指定的线程执行,只是 async 的 await 可以获取返回值
     * launch 和 async 切线程之后，当前线程继续执行，是并行的
     * 当遇到挂起函数时，可以自动切回来，在继续执行后面的代码(串行的)，比如 withContext async的await
     */
    private fun loadImg3() {
        CoroutineScope(Dispatchers.Main).launch {
            // 协程体
            sb.delete(0, sb.length)

            sb.append("1 ${Thread.currentThread().name} \n")

            async(Dispatchers.IO) {
                sb.append("11 ${Thread.currentThread().name} \n")
            }.await()

            repeat(100000) {}

            sb.append("2 ${Thread.currentThread().name} \n")

            val image = launch(Dispatchers.IO) {
                sb.append("3 ${Thread.currentThread().name} \n")
            }

            sb.append("4 ${Thread.currentThread().name} \n")

            delay(500)

            sb.append("5 ${Thread.currentThread().name} \n")

            launch(Dispatchers.Main) {
                sb.append("666 ${Thread.currentThread().name} \n")
                viewBinding.tvContent.text = sb.toString()
            }
        }
    }

    // 非 Fragment Activity ViewModel 环境下
    private fun job() {
        val job = RxLifeScope().launch({
            // 协程体
        }, {
            // 异常回调
        }, {
            // 协程开始回调
        }, {
            // 协程结束回调，不管成功/失败都会回调
        })
        //  在合适的时机，手动关闭协程
        job.cancel()
    }

}