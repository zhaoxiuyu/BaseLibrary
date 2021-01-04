package com.base.library.base

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.base.library.databinding.BaseLayoutBinding
import com.base.library.interfaces.MyTitleBarListener
import com.base.library.interfaces.MyXPopupListener
import com.base.library.mvvm.core.BViewModel
import com.base.library.mvvm.core.OnHandleCallback
import com.base.library.rxhttp.RxRequest
import com.billy.android.loading.Gloading
import com.blankj.utilcode.util.AdaptScreenUtils
import com.blankj.utilcode.util.BarUtils
import com.hjq.bar.TitleBar
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.interfaces.XPopupCallback
import com.zackratos.ultimatebarx.library.UltimateBarX
import rxhttp.wrapper.entity.Progress

abstract class BActivity : AppCompatActivity(), OnHandleCallback {

    abstract fun initArgs(mIntent: Intent?)
    abstract fun initView()
    abstract fun initData(savedInstanceState: Bundle?)
    abstract fun initObserve(): MutableList<BViewModel>?

    private val bBind by lazy { BaseLayoutBinding.inflate(layoutInflater) }

    val mApplication: BApplication by lazy { application as BApplication }
    private var xPopup: BasePopupView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initArgs(intent)

        initObserve()?.forEach { bViewModel ->
            bViewModel.getState().observe(this, Observer { state -> state.handler(this) })
        }

        initView()

        // window.decorView 获取到DecorView后,调用post方法,此时DecorView的attachInfo为空,
        // 会将这个Runnable放置runQueue中。runQueue内的任务会在ViewRootImpl.performTraversals的开始阶段被依次取出执行,
        // 这个方法内会执行到DecorView的测量、布局、绘制操作，不过runQueue的执行顺序会在这之前,所以需要再进行一次post操作
        // window.decorView.post { mHandler.post { initData() } }
        // IdleHandler在线程处于空闲的时候被执行,false 该回调进行移除,true 以后会多次调用
        Looper.myQueue().addIdleHandler {
            initData(savedInstanceState)
            false
        }
    }

    /**
     * --------------------- 通用的 TitleBar，避免每个 Activity 都复写一遍
     */
    fun getTitleBar() = bBind.titleBar

    /**
     * 默认有返回功能，如果不要返回 传listener实例 空实现就可以了。
     */
    fun setTitleBarOperation(
        title: String,
        listener: MyTitleBarListener? = null,
    ): TitleBar {
        getTitleBar().title = title
        if (listener == null) {
            getTitleBar().setOnTitleBarListener(object : MyTitleBarListener() {
                override fun onLeftClick(v: View?) {
                    finish()
                }
            })
        } else {
            getTitleBar().setOnTitleBarListener(listener)
        }
        return getTitleBar()
    }

    /**
     * 给 ContentView 的外面添加一个 通用的顶部导航栏
     */
    fun setContentViewBar(view: View, immersionBar: Boolean = true) {
        val lp = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        view.layoutParams = lp
        bBind.root.addView(view)

        // 如果是沉浸式，就把空白View的高度设置为状态栏的高度延伸上去
        if (immersionBar) immersionBar()

        setContentView(bBind.root)
    }

    fun immersionBar() {
        val stateBarLp = bBind.stateBar.layoutParams
        stateBarLp.height = BarUtils.getStatusBarHeight()
        bBind.stateBar.layoutParams = stateBarLp

        UltimateBarX.with(this).fitWindow(false).light(true).applyStatusBar()
    }

    /**
     * --------------------- 为指定 View 设置各种状态布局
     */
    var mGloadingHolder: Gloading.Holder? = null
    fun getGloadingHolder() = mGloadingHolder

    fun setGloading(view: View) {
        mGloadingHolder = Gloading.getDefault().wrap(view)
    }

    /**
     * --------------------- 获取新的值需要重新 setIntent
     */
//    override fun onNewIntent(intent: Intent?) {
//        super.onNewIntent(intent)
//        setIntent(intent)
//    }

    /**
     * --------------------- 用来做屏幕适配用的
     */
    override fun getResources(): Resources {
        return AdaptScreenUtils.adaptWidth(super.getResources(), 1080)
    }

    /**
     * --------------------- 提示框
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

    /**
     * --------------------- 状态的回调
     */
    override fun onLoading(mRequest: RxRequest) {
        Log.d("OnHandleCallback", "onLoading")
        if (mRequest.showLoading) {
            showLoading(null, mRequest.msg)
        }
    }

    override fun onSuccess(mRequest: RxRequest) {
        Log.d("OnHandleCallback", "onSuccess")
        // 网络请求是否弹出加载框，就对应的关闭
        if (mRequest.showLoading) dismissDialog()
        // 请求成功，是否弹窗提示
        if (mRequest.showSuccess) {
            val mListener = getDismissFinish(mRequest.successClickFinish)
            showDialog(content = mRequest.msg, confirmLi = mListener)
        }
    }

    override fun onError(mRequest: RxRequest) {
        Log.d("OnHandleCallback", "onError")
        // 网络请求是否弹出加载框，就对应的关闭
        if (mRequest.showLoading) dismissDialog()
        // 请求失败，是否弹窗提示
        if (mRequest.showFail) {
            val mListener = getDismissFinish(mRequest.failClickFinish)
            showDialog(content = mRequest.msg, confirmLi = mListener)
        }
    }

    override fun onCompleted() {
        Log.d("OnHandleCallback", "onCompleted")
    }

    override fun onProgress(progress: Progress?) {
        Log.d("OnHandleCallback", "onProgress")
    }

    /**
     * --------------------- 结束,清理
     */
    override fun onDestroy() {
        dismissDialog(false)
        super.onDestroy()
    }

}