package com.base.app.module.demos.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.base.app.base.MyConstant
import com.base.app.databinding.FragmentFunctionBinding
import com.base.app.module.demos.adapter.FragmentAdapter
import com.base.app.utils.MethodDatas
import com.base.library.base.BFragment
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener

class FunctionFragment : BFragment(), OnItemClickListener {

    private val mBind by lazy { FragmentFunctionBinding.inflate(layoutInflater) }

    private var param1: String? = null
    private var param2: String? = null

    private val mAdapter by lazy { FragmentAdapter() }

    override fun initArgs(bundle: Bundle?) {
        arguments?.let {
            param1 = it.getString(MyConstant.ARG_PARAM1)
            param2 = it.getString(MyConstant.ARG_PARAM2)
        }
    }

    override fun initView() = mBind.root

    override fun initData(bundle: Bundle?) {
        mBind.demosFunctionFragmentRv?.layoutManager = LinearLayoutManager(requireActivity())
        mBind.demosFunctionFragmentRv?.adapter = mAdapter
        mBind.demosFunctionFragmentRv?.addItemDecoration(
            MethodDatas.getDividerLinear(
                requireActivity()
            )
        )

        mAdapter.animationEnable = true
        mAdapter.setOnItemClickListener(this)
        mAdapter.setNewInstance(MethodDatas.getFunctionDescribe())

        LogUtils.d("FunctionFragment")
    }

    override fun initObserve() = null

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        mAdapter.getItem(position).cls?.let {
            startActivity(Intent(requireActivity(), it))
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FunctionFragment().apply {
                arguments = Bundle().apply {
                    putString(MyConstant.ARG_PARAM1, param1)
                    putString(MyConstant.ARG_PARAM2, param2)
                }
            }
    }
}