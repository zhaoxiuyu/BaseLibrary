package com.base.app.sample.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.base.app.base.MyARoute
import com.base.app.base.MyMethod
import com.base.app.databinding.FragmentViewsBinding
import com.base.library.mvvm.VMFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener

// view 功能列表
@Route(path = MyARoute.View_ViewsFragment)
class ViewsFragment : VMFragment<FragmentViewsBinding>(), OnItemClickListener {

    private val mAdapter by lazy { ViewsAdapter() }

    override fun initArgs(mArguments: Bundle?) {
    }

    override fun initView() {
        setContentView(viewBinding.root, viewBinding.viewsFragmentRv)
    }

    override fun initData(savedInstanceState: Bundle?) {
        viewBinding.viewsFragmentRv.layoutManager = LinearLayoutManager(requireActivity())
        viewBinding.viewsFragmentRv.adapter = mAdapter

        mAdapter.animationEnable = true
        mAdapter.setOnItemClickListener(this)
        mAdapter.setNewInstance(MyMethod.getViewsDescribe())
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