package com.base.app.module.demos.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.base.app.base.MyConstant
import com.base.app.databinding.FragmentUtilsBinding
import com.base.app.module.common.ui.MainActivity
import com.base.app.module.demos.adapter.FragmentAdapter
import com.base.app.utils.MethodDatas
import com.base.library.base.BFragment
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener

/**
 * Utils 工具类列表
 */
class UtilsFragment : BFragment(), OnItemClickListener {

    private val mBind by lazy { FragmentUtilsBinding.inflate(layoutInflater) }

    private var param1: String? = null
    private var param2: String? = null

    private val mAdapter by lazy { FragmentAdapter() }

    override fun initArgs(mArguments: Bundle?) {
        mArguments?.let {
            param1 = it.getString(MyConstant.ARG_PARAM1)
            param2 = it.getString(MyConstant.ARG_PARAM2)
            LogUtils.d(mArguments.getString("topBarTitle"))
        }
    }

    override fun initView() {
        setContentView(mBind.root)
        setTitleBarOperation("工具类")
        getTitleBar().leftIcon = null
    }

    override fun initData(savedInstanceState: Bundle?) {
        (activity as MainActivity).getMainBnv(true)
        requireActivity().onBackPressedDispatcher.addCallback {
            isEnabled = true
            (activity as MainActivity).backMain("UtilsFragment")
        }

        mBind.demosUtilsFragmentRv.layoutManager = LinearLayoutManager(requireActivity())
        mBind.demosUtilsFragmentRv.adapter = mAdapter
        mBind.demosUtilsFragmentRv.addItemDecoration(
            MethodDatas.getDividerLinear(
                requireActivity()
            )
        )

        mAdapter.animationEnable = true
        mAdapter.setOnItemClickListener(this)
        mAdapter.setNewInstance(MethodDatas.getUtilsDescribe())
    }

    override fun initObserve(): Nothing? = null

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        mAdapter.getItem(position).cls?.let {
            startActivity(Intent(requireActivity(), it))
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UtilsFragment().apply {
                arguments = Bundle().apply {
                    putString(MyConstant.ARG_PARAM1, param1)
                    putString(MyConstant.ARG_PARAM2, param2)
                }
            }
    }

}