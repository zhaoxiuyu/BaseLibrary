package com.base.library.interfaces

import com.lxj.xpopup.interfaces.OnCancelListener
import com.lxj.xpopup.interfaces.OnConfirmListener

/**
 * 用来给 XPopup 公用同一个 确定 取消 回调事件的
 */
interface MyXPopupListener : OnConfirmListener, OnCancelListener {

    // 确定
    override fun onConfirm() {
        onDis()
    }

    // 取消
    override fun onCancel() {
        onDis()
    }

    fun onDis()

}