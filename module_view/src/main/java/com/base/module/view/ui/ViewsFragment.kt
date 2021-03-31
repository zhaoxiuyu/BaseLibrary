package com.base.module.view.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.base.library.base.BFragment
import com.base.module.common.module_views.ViewsARoute
import com.base.module.view.adapter.ViewsAdapter
import com.base.module.view.databinding.FragmentViewsBinding
import com.base.module.view.utils.ViewsMethod
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@Route(path = ViewsARoute.View_ViewsFragment)
@AndroidEntryPoint
class ViewsFragment : BFragment(), OnItemClickListener {

    private val mBind by lazy { FragmentViewsBinding.inflate(layoutInflater) }

    @Inject
    lateinit var mAdapter: ViewsAdapter

    override fun initArgs(mArguments: Bundle?) {
    }

    override fun initView() {
        setContentViewBar(mBind.root, false)
    }

    override fun initData(savedInstanceState: Bundle?) {
        mBind.viewsFragmentRv.layoutManager = LinearLayoutManager(requireActivity())
        mBind.viewsFragmentRv.adapter = mAdapter

        mAdapter.animationEnable = true
        mAdapter.setOnItemClickListener(this)
        mAdapter.setNewInstance(ViewsMethod.getViewsDescribe())
    }

    override fun registerObserve() {
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val item = mAdapter.getItem(position)
        item.cls?.let {
            startActivity(Intent(requireActivity(), it))
        }
    }

}