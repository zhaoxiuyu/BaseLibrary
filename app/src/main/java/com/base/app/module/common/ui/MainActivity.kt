package com.base.app.module.common.ui

import android.annotation.SuppressLint
import android.content.Intent
import com.base.app.R
import com.base.library.mvp.VPActivity
import com.base.library.mvp.core._VPPresenter
import com.base.library.mvp.core._VPView
import com.blankj.utilcode.util.FragmentUtils
import com.blankj.utilcode.util.LogUtils
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : VPActivity<_VPPresenter>(),
    _VPView {

    private val mainFragment: MainFragment by lazy { MainFragment() }

    override fun initArgs(intent: Intent?) {
    }

    override fun initView() {
        initContentView(R.layout.activity_main)
        mPresenter = _VPPresenter(this)
    }

    override fun onResume() {
        super.onResume()
        LogUtils.d("onResume")
    }

    @SuppressLint("MissingPermission")
    override fun initData() {

        LogUtils.d("initData")

        doOnNext.parent
//        val bRequest =
//            BRequest.Builder("http://apicloud.mob.com/v1/mobile/address/query?key=2747420d25758&phone=13300001982")
//                .setHttpType(BRequest.GET)
//                .builder()
//        mPresenter.getData(bRequest)

        doOnNext.setOnClickListener { doOnNext() }
        doOnSubscribe.setOnClickListener { doOnSubscribe() }
        show.setOnClickListener {
            FragmentUtils.add(supportFragmentManager, mainFragment, R.id.layout, false)
        }
        hide.setOnClickListener {
            FragmentUtils.remove(mainFragment)
        }
        butLogin.setOnClickListener {

            startActivity(Intent(this, LoginActivity::class.java))

        }
//        butRegister.setOnClickListener { startActivity(Intent(this, RegisterActivity::class.java)) }

        butRegister.setOnClickListener {
            var str: String? = null
            var str2: String? = "str2"
            str?.let {
                LogUtils.d("str不为空")
            } ?: let {
                LogUtils.d("str空")
            }

            str2?.let {
                LogUtils.d("str2不为空")
            } ?: let {
                LogUtils.d("str2空")
            }

        }
    }

    private fun doOnNext() {
        val froms = mutableListOf("1", "2", "3")
        Observable.fromIterable(froms)
            .subscribeOn(Schedulers.io())
//            .doOnEach {
//                LogUtils.d("doOnEach = ${Thread.currentThread().name} = $it")
//            }
            .map {
                LogUtils.d("map = ${Thread.currentThread().name} = $it")
                Thread.sleep(2000)
                it
            }
            .observeOn(AndroidSchedulers.mainThread())
            .`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
            .subscribe {
                LogUtils.d("subscribe = ${Thread.currentThread().name} = $it")
            }

    }

    private fun doOnSubscribe() {

//        Observable
//            .just("item")
//            .subscribeOn(Schedulers.io())
//            .doOnSubscribe(Consumer {
//                // doOnSubscribe 后面可以通过 subscribeOn 指定 执行线程,默认为主线程
//                LogUtils.d("${Thread.currentThread().name}")
//            })
////            .subscribeOn(Schedulers.io()) // 指定 doOnSubscribe 执行的线程
//            .observeOn(AndroidSchedulers.mainThread())
//            .`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
//            .subscribe(Consumer {
//                LogUtils.d("${Thread.currentThread().name}")
//            })
    }

    fun xc() {
        GlobalScope.launch {
            // async 启动新协程,3个同时请求,无需等待前一个请求的结果
            val str1 = async { getStr("str1") }
            val str1Await = str1.await()
            LogUtils.d("1")

            val str2 = async { getStr("str2") }
            val str2Await = str2.await()
            LogUtils.d("2")

            val str3 = async { getStr("str3") }
            val str3Await = str3.await()
            LogUtils.d("3")

            // await 协程挂起,等3个请求全部返回了在组装数据
            printStr(str1Await, str2Await, str3Await)

            LogUtils.d("4")
        }
    }

    suspend fun getStr(str: String): String {
        LogUtils.d("${Thread.currentThread().name} + $str")
        if ("str2" == str) delay(3000)
        return str
    }

    fun printStr(str1: String, str2: String, str3: String) {
        LogUtils.d("${Thread.currentThread().name} + $str1 = $str2 = $str3")
    }

    suspend fun getStr2(str: String) = withContext(Dispatchers.IO) {
        return@withContext str
    }

    override fun bindData(any: Any) {
    }

    override fun bindError(string: String) {
    }

    /**
     * 协程就是切线程
     * 挂起就是可以自动切回来的切线程
     * 挂起的非阻塞式就是用看起来的阻塞的代码写出非阻塞的操作
     */

}
