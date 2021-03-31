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
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@Route(path = UtilsARoute.Utils_UtilsFragment)
@AndroidEntryPoint
class UtilsFragment : BFragment(), OnItemClickListener {

    private val mBind by lazy { FragmentUtilsBinding.inflate(layoutInflater) }

    @Inject
    lateinit var mAdapter: UtilsAdapter

    override fun initArgs(mArguments: Bundle?) {
    }

    override fun initView() {
        setContentViewBar(mBind.root, false)
    }

    override fun initData(savedInstanceState: Bundle?) {
        mBind.utilsFragmentRv.layoutManager = LinearLayoutManager(requireActivity())
        mBind.utilsFragmentRv.adapter = mAdapter

        mAdapter.animationEnable = true
        mAdapter.setOnItemClickListener(this)
        mAdapter.setNewInstance(UtilsMethod.getUtilsDescribe())
    }

    override fun registerObserve() {
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
    }

}