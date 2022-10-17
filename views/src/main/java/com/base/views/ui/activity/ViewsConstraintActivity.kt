package com.base.views.ui.activity

import android.content.Intent
import android.os.Bundle
import com.base.library.mvvm.VMActivity
import com.base.views.R
import com.base.views.databinding.ViewsActivityConstraintBinding

class ViewsConstraintActivity : VMActivity<ViewsActivityConstraintBinding>() {

    override fun initArgs(mIntent: Intent?) {
    }

    override fun initView() {
        setContentViewBar(viewBinding.root)
        viewBinding.tv10.setOnClickListener {
            viewBinding.placeholder1.setContentId(R.id.tv10)
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun registerObserve() {
    }

}