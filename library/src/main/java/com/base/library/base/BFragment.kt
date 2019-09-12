package com.base.library.base

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.base.library.util.getCacheObservable
import com.base.library.util.putCacheObservable
import com.blankj.utilcode.util.LogUtils
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.components.ImmersionFragment
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.interfaces.OnCancelListener
import com.lxj.xpopup.interfaces.OnConfirmListener
import com.lxj.xpopup.interfaces.XPopupCallback
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.base.library.mvp.BPresenter
import com.base.library.mvp.BView
import io.reactivex.functions.Consumer

/**
 * ImmersionOwner 用来在Fragment中实现沉浸式
 */
abstract class BFragment<T : BPresenter> : ImmersionFragment(), BView {
//abstract class BFragment<T : BPresenter> : Fragment(), BView {

    abstract fun initArgs(bundle: Bundle?)
    abstract fun initView(bundle: Bundle?)
    abstract fun initData()

    var mPresenter: T? = null
    private var xPopup: BasePopupView? = null

    private var mView: View? = null
    private var container: ViewGroup? = null
    private var inflater: LayoutInflater? = null

    val mHandler: Handler by lazy { Handler() }
    val mApplication: BApplication by lazy { activity?.application as BApplication }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.inflater = inflater
        this.container = container

        initArgs(arguments)
        initView(savedInstanceState)
        mPresenter?.let { lifecycle.addObserver(it) }
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    override fun initImmersionBar() {
        ImmersionBar.with(this).titleBar(mView).init()
    }

    fun setContentView(layout: Int) {
        mView = inflater?.inflate(layout, container, false)
    }

    override fun onDestroyView() {
        disDialog()
        mHandler.removeCallbacksAndMessages(null)
        super.onDestroyView()
    }

    override fun onDetach() {
        val childFragmentManager = Fragment::class.java.getDeclaredField("mChildFragmentManager")
        childFragmentManager.isAccessible = true
        childFragmentManager.set(this, null)
        super.onDetach()
    }

    override fun getContext() = activity!!

    override fun showDialog(xPopupCallback: XPopupCallback?, loading: String?) {
        xPopup = XPopup.Builder(activity)
            .dismissOnBackPressed(false)
            .dismissOnTouchOutside(false)
            .setPopupCallback(xPopupCallback).asLoading(loading).show()
    }

    override fun showDialog(
        title: String?,
        content: String?,
        cancelBtnText: String?,
        confirmBtnText: String?,
        confirmListener: OnConfirmListener?,
        cancelListener: OnCancelListener?,
        xPopupCallback: XPopupCallback?,
        isHideCancel: Boolean
    ) {
        disDialog()
        xPopup = XPopup.Builder(activity).setPopupCallback(xPopupCallback)
            .dismissOnBackPressed(false).dismissOnTouchOutside(false)
            .asConfirm(title, content, cancelBtnText, confirmBtnText, confirmListener, cancelListener, isHideCancel)
            .show()
    }

    //确定 - 关闭提示框
    override fun getConfirmDisListener(): OnConfirmListener = OnConfirmListener { disDialog() }

    //确定 - 关闭页面
    override fun getConfirmFinishListener(): OnConfirmListener = OnConfirmListener { dismissWith() }

    //取消 - 关闭提示框
    override fun getCancelDisListener(): OnCancelListener = OnCancelListener { disDialog() }

    //取消 - 关闭页面
    override fun getCancelFinishListener(): OnCancelListener = OnCancelListener { dismissWith() }

    //关闭提示框
    override fun disDialog() {
        xPopup?.dismiss()
    }

    //关闭提示框之后销毁页面
    override fun dismissWith() {
        xPopup?.dismissWith { activity?.finish() }
    }

    //保存缓存
    override fun putCache(key: String, content: String, time: Int) {
        putCacheObservable(key, content, time)
            .`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
            .subscribe { LogUtils.d(it) }
    }

    //获取缓存
    override fun getCache(key: String, consumer: Consumer<String>) {
        getCacheObservable(key)
            .`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
            .subscribe(consumer)
    }
}