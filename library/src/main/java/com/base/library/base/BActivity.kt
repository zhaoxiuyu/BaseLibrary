package com.base.library.base

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.base.library.R
import com.base.library.interfaces.MyXPopupListener
import com.base.library.mvvm.core.VMViewModel
import com.base.library.view.BTitleBar
import com.blankj.utilcode.util.BusUtils
import com.blankj.utilcode.util.CacheDiskStaticUtils
import com.blankj.utilcode.util.LogUtils
import com.gyf.immersionbar.ImmersionBar
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.interfaces.XPopupCallback
import com.rxjava.rxlife.lifeOnMain
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.base_activity_layout.*

abstract class BActivity : AppCompatActivity() {

    abstract fun initArgs(intent: Intent?): VMViewModel?
    abstract fun initView()
    abstract fun lazyData()

    var vm: VMViewModel? = null
    val mApplication: BApplication by lazy { application as BApplication }
    private var xPopup: BasePopupView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm = initArgs(intent)

        initView()
        initImmersionBar()

        // window.decorView 获取到DecorView后,调用post方法,此时DecorView的attachInfo为空,
        // 会将这个Runnable放置runQueue中。runQueue内的任务会在ViewRootImpl.performTraversals的开始阶段被依次取出执行,
        // 这个方法内会执行到DecorView的测量、布局、绘制操作，不过runQueue的执行顺序会在这之前,所以需要再进行一次post操作
        // window.decorView.post { mHandler.post { initData() } }
        // IdleHandler在线程处于空闲的时候被执行,false 该回调进行移除,true 以后会多次调用
        Looper.myQueue().addIdleHandler {
            lazyData()
            false
        }

        BusUtils.register(this)
    }

    fun getBTitleBar(): BTitleBar? = bTitleBar

    // 沉浸式,如果不要沉浸式可以重写这个方法空实现即可
    open fun initImmersionBar() {
        ImmersionBar.with(this).titleBar(getBTitleBar()).init()
    }

    open fun setContentViewBar(layoutResID: Int) {
        setContentView(R.layout.base_activity_layout)
        val contentView = LayoutInflater.from(this).inflate(layoutResID, bLLayout, false)
        bLLayout.addView(contentView)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    /**
     * ------------- 文件缓存 -------------
     */
    open fun getCacheDisk(key: String, consumer: Consumer<String>) {
        Observable.just("获取缓存").map { CacheDiskStaticUtils.getString(key, "") }
            .subscribeOn(Schedulers.io()).lifeOnMain(this).subscribe(consumer)
    }

    open fun putCacheDisk(key: String, content: String, time: Int) {
        Observable.just("保存缓存")
            .map {
                CacheDiskStaticUtils.put(key, content, time)
                "$key 缓存成功"
            }
            .subscribeOn(Schedulers.io()).lifeOnMain(this).subscribe { LogUtils.d(it) }
    }

    /**
     * ------------- 提示框 -------------
     */
    fun showLoading(xPopupCallback: XPopupCallback? = null, msg: String? = "请稍候") {
        xPopup?.dismiss()

        xPopup = XPopup.Builder(this)
            .dismissOnBackPressed(false)
            .dismissOnTouchOutside(false)
            .setPopupCallback(xPopupCallback).asLoading(msg)

        if (xPopup?.isDismiss == true) xPopup?.show()
    }

    fun showDialog(
        title: String? = "提示",
        content: String? = "暂无内容",
        cancelTx: String? = "取消",
        confirmTx: String? = "确定",
        confirmLi: MyXPopupListener? = null,
        cancelLi: MyXPopupListener? = null,
        isHideCancel: Boolean = true,
        callback: XPopupCallback? = null
    ) {
        xPopup?.dismiss()

        xPopup = XPopup.Builder(this).setPopupCallback(callback)
            .dismissOnBackPressed(false).dismissOnTouchOutside(false)
            .asConfirm(title, content, cancelTx, confirmTx, confirmLi, cancelLi, isHideCancel)
        xPopup?.show()
    }

    fun getDismissFinish(isFinish: Boolean, runnable: Runnable? = null): MyXPopupListener =
        object : MyXPopupListener {
            override fun onDis() {
                dismissDialog(isFinish, runnable)
            }
        }

    fun dismissDialog(isFinish: Boolean = false, runnable: Runnable? = null) {
        xPopup?.dismissWith {
            runnable?.run()
            if (isFinish) {
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dismissDialog(false)
        BusUtils.unregister(this)
    }

}