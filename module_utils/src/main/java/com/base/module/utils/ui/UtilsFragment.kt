package com.base.module.utils.ui

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.base.library.base.BFragment
import com.base.module.common.module_utils.UtilsARoute
import com.base.module.utils.adapter.UtilsAdapter
import com.base.module.utils.databinding.FragmentUtilsBinding
import com.base.module.utils.utils.UtilsMethod
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener

@Route(path = UtilsARoute.Utils_UtilsFragment)
class UtilsFragment : BFragment(), OnItemClickListener {

    private val viewBinding by lazy { FragmentUtilsBinding.inflate(layoutInflater) }

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
        mAdapter.setNewInstance(UtilsMethod.getUtilsDescribe())
    }

    override fun registerObserve() {
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
    }

}