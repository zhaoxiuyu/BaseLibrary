package com.base.app.sample.views

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.base.app.R
import com.base.app.base.MyARoute
import com.base.app.base.MyMethod
import com.base.app.databinding.FragmentViewsBinding
import com.base.app.entitys.PageDescribe
import com.base.library.mvvm.VMFragment
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup

// view 功能列表
@Route(path = MyARoute.View_ViewsFragment)
class ViewsFragment : VMFragment<FragmentViewsBinding>() {

    override fun initArgs(mArguments: Bundle?) {
    }

    override fun initView() {
        setContentView(viewBinding.root, viewBinding.viewsFragmentRv)
    }

    override fun initData(savedInstanceState: Bundle?) {
        initAdapter()
    }

    override fun registerObserve() {
    }

    private fun initAdapter() {
        viewBinding.viewsFragmentRv.linear().setup {
            addType<PageDescribe>(R.layout.fragment_views_item)
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
        }.models = MyMethod.getViewsDescribe()
    }
}