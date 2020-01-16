package com.base.app.module.common.ui

import android.annotation.SuppressLint
import android.content.Intent
import com.base.app.R
import com.base.library.base.BaseActivity
import com.base.library.dao.LitePalUtils
import com.base.library.dao.entity.Journal
import com.base.library.mvp.BasePresenter
import com.base.library.mvp.BaseView
import com.blankj.utilcode.util.FragmentUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ThreadUtils
import com.lxj.xpopup.interfaces.OnCancelListener
import com.lxj.xpopup.interfaces.OnConfirmListener
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class MainActivity : BaseActivity<BasePresenter>(), BaseView {

    private val mainFragment: MainFragment by lazy { MainFragment() }

    override fun initArgs(intent: Intent?) {
    }

    override fun initView() {
        initContentView(R.layout.activity_main)
        mPresenter = BasePresenter(this)
    }

    override fun onResume() {
        super.onResume()
        LogUtils.d("onResume")
    }

    @SuppressLint("MissingPermission")
    override fun initData() {

        LogUtils.d("initData")

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
            //            xc()
        }

        butDialog.setOnClickListener {
            LogUtils.d("123")

            showDialog(
                title = "测试",
                content = "取消退出,确定重试",
                confirmListener = OnConfirmListener {
                    showDialog(
                        title = "测试",
                        content = "我点击了确定",
                        confirmListener = getDismissListener(),
                        cancelListener = getDismissListener(),
                        isHideCancel = true
                    )
                },
                cancelListener = OnCancelListener {
                    showDialog(
                        title = "测试",
                        content = "我点击了取消",
                        confirmListener = getDismissListener(),
                        cancelListener = getDismissListener(),
                        isHideCancel = true
                    )
                },
                isHideCancel = false
            )
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
        val myThread = thread(start = false) {
            LogUtils.d("1主线程${ThreadUtils.isMainThread()}")
        }
        myThread.start()

        GlobalScope.launch {
            LogUtils.d("2主线程${ThreadUtils.isMainThread()}")
        }

    }

    override fun bindData(any: Any) {
    }

    override fun bindError(string: String) {
    }

}
