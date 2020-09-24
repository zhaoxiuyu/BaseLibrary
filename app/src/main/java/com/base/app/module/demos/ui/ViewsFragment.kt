package com.base.app.module.demos.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.base.app.R
import com.base.app.base.MyConstant
import com.base.app.module.demos.adapter.FragmentAdapter
import com.base.app.utils.MethodDatas
import com.base.library.mvvm.core.VMFragment
import com.base.library.mvvm.core.VMViewModel
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import kotlinx.android.synthetic.main.fragment_views.*

/**
 * Views 控件类列表
 */
class ViewsFragment : VMFragment(), OnItemClickListener {

    private var param1: String? = null
    private var param2: String? = null

    private val mAdapter by lazy { FragmentAdapter() }

    override fun getContentView() = R.layout.fragment_views

    override fun initArgs(bundle: Bundle?): VMViewModel? {
        arguments?.let {
            param1 = it.getString(MyConstant.ARG_PARAM1)
            param2 = it.getString(MyConstant.ARG_PARAM2)
        }
        return null
    }

    override fun initData(bundle: Bundle?) {
        demos_views_fragment_rv.layoutManager = LinearLayoutManager(requireActivity())
        demos_views_fragment_rv.adapter = mAdapter
        demos_views_fragment_rv.addItemDecoration(MethodDatas.getDividerLinear(requireActivity()))

        mAdapter.animationEnable = true
        mAdapter.setOnItemClickListener(this)
        mAdapter.setNewInstance(MethodDatas.getViewsDescribe())
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