package com.zxy.library.framework.template.ui

import android.content.Intent
import com.base.library.base.BActivity
import com.base.library.entitys.BaseResponse
import com.zxy.library.framework.mvp.BPresenter
import com.zxy.library.framework.mvp.BasePresenter
import com.zxy.library.framework.mvp.BaseView

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