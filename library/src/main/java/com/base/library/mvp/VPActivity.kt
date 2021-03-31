package com.base.library.mvp

import android.os.Bundle
import com.base.library.base.BActivity
import com.base.library.interfaces.MyXPopListener
import com.base.library.mvp.core.VPView
import com.lxj.xpopup.interfaces.OnCancelListener
import com.lxj.xpopup.interfaces.OnConfirmListener
import com.lxj.xpopup.interfaces.XPopupCallback

/**
 * MVP 模式的基础 Activity
 */
abstract class VPActivity : BActivity(), VPView {

//    var mPresenter: T? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        mPresenter?.let { lifecycle.addObserver(it) }
    }

    override fun registerObserve() {
    }

    override fun getContext() = this

    override fun showDialog(xPopupCallback: XPopupCallback?, loading: String?) {
//        showLoading(xPopupCallback, loading)
    }

    override fun showDialog(
        title: String?,
        content: String?,
        cancelTx: String?,
        confirmTx: String?,
        confirmLi: OnConfirmListener?,
        cancelLi: OnCancelListener?,
        isHideCancel: Boolean?,
        callback: XPopupCallback?
    ) {
        showDialog(title, content, cancelTx, confirmTx, confirmLi, cancelLi, isHideCancel, callback)
    }

    // 关闭提示框 并且 销毁页面
    override fun getDismissFinishListener(): MyXPopListener = object : MyXPopListener {
        override fun onDis() {
            dismissDialog(true)
        }
    }

    // 关闭提示框,是否销毁页面
    override fun disDialog(finish: Boolean) {
        dismissDialog(finish)
    }

    //P层的数据回调,可以做一些日志保存
    override fun other(content: String, behavior: String, level: String) {
    }

}