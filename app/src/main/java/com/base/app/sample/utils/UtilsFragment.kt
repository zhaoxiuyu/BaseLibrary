package com.base.app.sample.utils

import android.os.Bundle
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.base.app.R
import com.base.app.base.MyARoute
import com.base.app.base.MyMethod
import com.base.app.databinding.FragmentUtilsBinding
import com.base.app.entitys.PageDescribe
import com.base.library.mvvm.VMFragment
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup

@Route(path = MyARoute.Utils_UtilsFragment)
class UtilsFragment : VMFragment<FragmentUtilsBinding>() {

    override fun initArgs(mArguments: Bundle?) {
    }

    override fun initView() {
        setContentView(viewBinding.root)
    }

    override fun initData(savedInstanceState: Bundle?) {
        initAdapter()
    }

    override fun registerObserve() {
    }

    private fun initAdapter() {
        viewBinding.utilsFragmentRv.linear().setup {
            addType<PageDescribe>(R.layout.fragment_utils_item)
            onBind {
                val model = getModel<PageDescribe>()
                findView<TextView>(R.id.material_textview_name).text = model.name
                findView<TextView>(R.id.material_textview_describe).text = model.describe
            }
        }.models = MyMethod.getUtilsDescribe()
    }
}