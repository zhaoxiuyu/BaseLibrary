package com.base.views.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.base.common.constant.CommonARoute
import com.base.common.entitys.PageDescribe
import com.base.library.mvvm.VMFragment
import com.base.views.R
import com.base.views.constant.ViewsMethod
import com.base.views.databinding.ViewsFragmentBinding
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup

// view 功能列表
@Route(path = CommonARoute.View_ViewsFragment)
class ViewsFragment : VMFragment<ViewsFragmentBinding>() {

    override fun initArgs(mArguments: Bundle?) {
    }

    override fun initData(savedInstanceState: Bundle?) {
        addStatusBarTopPadding(viewBinding.root)
        initAdapter()
    }

    override fun registerObserve() {
    }

    private fun initAdapter() {
        viewBinding.viewsFragmentRv.linear().setup {
            addType<PageDescribe>(R.layout.views_fragment_item)
            onBind {
                val model = getModel<PageDescribe>()
                findView<TextView>(R.id.material_textview_name).text = model.name
                findView<TextView>(R.id.material_textview_describe).text = model.describe
            }
            onClick(R.id.itemView) {
                val model = getModel<PageDescribe>()
                model.cls?.let {
                    startActivity(Intent(requireActivity(), it))
                }
            }
        }.models = ViewsMethod.getViewsDescribe()
    }

}