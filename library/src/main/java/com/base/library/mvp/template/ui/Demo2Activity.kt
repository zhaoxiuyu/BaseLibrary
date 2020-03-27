package com.base.library.mvp.template.ui

import android.content.Intent
import com.base.library.base.VPActivity
import com.base.library.mvp.VPPresenter
import com.base.library.mvp._VPPresenter
import com.base.library.mvp._VPView

/**
 * 作用: 使用案例,使用通用的P和V
 */
class Demo2Activity : VPActivity<VPPresenter>(), _VPView {

    override fun initArgs(intent: Intent?) {
    }

    override fun initView() {
    }

    override fun initData() {
        mPresenter = _VPPresenter(this)
    }

    override fun bindData(any: Any) {
    }

    override fun bindError(string: String) {
    }

}