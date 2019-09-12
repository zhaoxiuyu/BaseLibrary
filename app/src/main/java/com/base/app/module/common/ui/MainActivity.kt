package com.base.app.module.common.ui

import android.annotation.SuppressLint
import android.content.Intent
import com.base.app.R
import com.base.library.base.BActivity
import com.base.library.entitys.BaseResponse
import com.base.library.mvp.BPresenter
import com.base.library.mvp.BasePresenter
import com.base.library.mvp.BaseView
import com.blankj.utilcode.util.FragmentUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BActivity<BPresenter>(), BaseView {

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
    }

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

    override fun bindData(baseResponse: BaseResponse) {
    }

    override fun bindError(string: String) {
    }

}
