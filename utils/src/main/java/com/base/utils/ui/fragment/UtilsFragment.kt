package com.base.utils.ui.fragment

import android.os.Bundle
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.base.common.constant.CommonARoute
import com.base.common.entitys.PageDescribe
import com.base.library.mvvm.VMFragment
import com.base.utils.R
import com.base.utils.constant.UtilsMethod
import com.base.utils.databinding.UtilsFragmentBinding
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup

@Route(path = CommonARoute.Utils_UtilsFragment)
class UtilsFragment : VMFragment<UtilsFragmentBinding>() {

    override fun initArgs(mArguments: Bundle?) {
    }

    override fun initData(savedInstanceState: Bundle?) {
        addStatusBarTopPadding(viewBinding.root)
        initAdapter()
    }

    override fun registerObserve() {
    }

    private fun initAdapter() {
        viewBinding.utilsFragmentRv.linear().setup {
            addType<PageDescribe>(R.layout.utils_fragment_item)
            onBind {
                val model = getModel<PageDescribe>()
                findView<TextView>(R.id.material_textview_name).text = model.name
                findView<TextView>(R.id.material_textview_describe).text = model.describe
            }
        }.models = UtilsMethod.getUtilsDescribe()
    }

}