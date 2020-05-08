package com.base.library.mvp.core

/**
 * 作用: 通用的回调层
 * 如果你不想自定义Contract层，可以直接使用这个类
 */
interface _VPView : VPView {

    fun bindData(body: Any)

    fun bindError(string: String)

}