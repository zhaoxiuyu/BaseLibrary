package com.base.app.module.demos.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.base.app.base.MyConstant
import com.base.app.databinding.FragmentUtilsBinding
import com.base.app.databinding.FragmentViewsBinding
import com.base.app.module.demos.adapter.FragmentAdapter
import com.base.app.utils.MethodDatas
import com.base.library.mvvm.core.VMFragment
import com.base.library.mvvm.core.VMViewModel
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener

/**
 * Views 控件类列表
 */
class ViewsFragment : VMFragment(), OnItemClickListener {

    private val mBind by lazy { FragmentViewsBinding.inflate(layoutInflater) }

    private var param1: String? = null
    private var param2: String? = null

    private val mAdapter by lazy { FragmentAdapter() }

    override fun initArgs(bundle: Bundle?): VMViewModel? {
        arguments?.let {
            param1 = it.getString(MyConstant.ARG_PARAM1)
            param2 = it.getString(MyConstant.ARG_PARAM2)
        }
        return null
    }

    override fun initView() = mBind.root

    override fun initData(bundle: Bundle?) {
        mBind.demosViewsFragmentRv.layoutManager = LinearLayoutManager(requireActivity())
        mBind.demosViewsFragmentRv.adapter = mAdapter
        mBind.demosViewsFragmentRv.addItemDecoration(MethodDatas.getDividerLinear(requireActivity()))

        mAdapter.animationEnable = true
        mAdapter.setOnItemClickListener(this)
        mAdapter.setNewInstance(MethodDatas.getViewsDescribe())

        LogUtils.d("ViewsFragment")
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        mAdapter.getItem(position).cls?.let {
            startActivity(Intent(requireActivity(), it))
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ViewsFragment().apply {
                arguments = Bundle().apply {
                    putString(MyConstant.ARG_PARAM1, param1)
                    putString(MyConstant.ARG_PARAM2, param2)
                }
            }
    }
}