package com.base.app.sample.utils

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.base.app.base.MyARoute
import com.base.app.base.MyMethod
import com.base.app.databinding.FragmentUtilsBinding
import com.base.library.mvvm.VMFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener

@Route(path = MyARoute.Utils_UtilsFragment)
class UtilsFragment : VMFragment<FragmentUtilsBinding>(), OnItemClickListener {

    private val mAdapter by lazy { UtilsAdapter() }

    override fun initArgs(mArguments: Bundle?) {
    }

    override fun initView() {
        setContentView(viewBinding.root, true, viewBinding.utilsFragmentRv)
    }

    override fun initData(savedInstanceState: Bundle?) {
        viewBinding.utilsFragmentRv.layoutManager = LinearLayoutManager(requireActivity())
        viewBinding.utilsFragmentRv.adapter = mAdapter

        mAdapter.animationEnable = true
        mAdapter.setOnItemClickListener(this)
        mAdapter.setNewInstance(MyMethod.getUtilsDescribe())
    }

    override fun registerObserve() {
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
    }

}