package com.base.library.template.ui

import android.content.Intent
import com.base.library.base.BActivity
import com.base.library.entitys.BaseResponse
import com.base.library.mvp.BPresenter
import com.base.library.mvp.BasePresenter
import com.base.library.mvp.BaseView

/**
 * 作用: 使用案例,使用通用的P和V
 */
class Demo2Activity : BActivity<BPresenter>(), BaseView {

    override fun initArgs(intent: Intent?) {
    }

    override fun initView() {
    }

    override fun initData() {
        mPresenter = BasePresenter(this)
    }

    override fun bindData(baseResponse: BaseResponse) {
    }

    override fun bindError(string: String) {
    }

}