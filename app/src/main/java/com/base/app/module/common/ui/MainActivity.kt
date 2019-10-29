package com.base.app.module.common.ui

import android.annotation.SuppressLint
import android.content.Intent
import com.base.app.R
import com.base.app.module.personal.ui.RegisterActivity
import com.base.library.base.BActivity
import com.base.library.mvp.BasePresenter
import com.base.library.mvp.BaseView
import com.blankj.utilcode.util.FragmentUtils
import com.blankj.utilcode.util.LogUtils
import com.lxj.xpopup.interfaces.OnCancelListener
import com.lxj.xpopup.interfaces.OnConfirmListener
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BActivity<BasePresenter>(), BaseView {

    private val mainFragment: MainFragment by lazy { MainFragment() }

    override fun initArgs(intent: Intent?) {
    }

    override fun initView() {
        initContentView(R.layout.activity_main)
        mPresenter = BasePresenter(this)
    }

    @SuppressLint("MissingPermission")
    override fun initData() {

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
        butLogin.setOnClickListener { startActivity(Intent(this, LoginActivity::class.java)) }
        butRegister.setOnClickListener { startActivity(Intent(this, RegisterActivity::class.java)) }
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

    var disposable: Disposable? = null

    private fun doOnNext() {
//        val br = BaseResponse()
//        br.code = "10010"
//        Observable
//            .just(br)
//            .doOnNext {
//                it.code = "1008611"
//            }
//            .`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
//            .subscribe {
//                LogUtils.d("${it.code}")
//            }

        val froms = mutableListOf("1", "2", "3")
        Observable.fromIterable(froms)
            .map {
                LogUtils.d("map = $it")
                it
            }
            .`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
            .subscribe(object : Observer<String> {
                override fun onComplete() {
                    LogUtils.d("onComplete")
                }

                override fun onSubscribe(d: Disposable) {
                    disposable = d
                    LogUtils.d("onSubscribe")
                }

                override fun onNext(t: String) {
                    if (t == "2") disposable?.dispose()
                    LogUtils.d("onNext")
                }

                override fun onError(e: Throwable) {
                }
            })
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


    override fun bindData(any: Any) {
    }

    override fun bindError(string: String) {
    }

}
