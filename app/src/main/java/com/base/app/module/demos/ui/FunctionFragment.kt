package com.base.app.module.demos.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.base.app.R
import com.base.app.base.MyConstant
import com.base.app.databinding.FragmentFunctionBinding
import com.base.app.module.common.ui.MainActivity
import com.base.app.module.demos.adapter.FragmentAdapter
import com.base.app.utils.MethodDatas
import com.base.library.base.BFragment
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener

/**
 * 其他测试 列表
 */
class FunctionFragment : BFragment(), OnItemClickListener {

    private val mBind by lazy { FragmentFunctionBinding.inflate(layoutInflater) }

    private var param1: String? = null
    private var param2: String? = null

    private val mAdapter by lazy { FragmentAdapter() }

    private var mDispatcherEnabled = true

    override fun initArgs(mArguments: Bundle?) {
        mArguments?.let {
            param1 = it.getString(MyConstant.ARG_PARAM1)
            param2 = it.getString(MyConstant.ARG_PARAM2)
            LogUtils.d(mArguments.getString("topBarTitle"))
        }
    }

    override fun initView() {
        setContentView(mBind.root)
        setTitleBarOperation("测试功能")
        getTitleBar().leftIcon = null
    }

    override fun initData(savedInstanceState: Bundle?) {
        (activity as MainActivity).getMainBnv(true)
        requireActivity().onBackPressedDispatcher.addCallback {
            isEnabled = mDispatcherEnabled
            (activity as MainActivity).backMain("FunctionFragment")
        }

        mBind.demosFunctionFragmentRv.layoutManager = LinearLayoutManager(requireActivity())
        mBind.demosFunctionFragmentRv.adapter = mAdapter
        mBind.demosFunctionFragmentRv.addItemDecoration(
            MethodDatas.getDividerLinear(requireActivity())
        )

        mAdapter.animationEnable = true
        mAdapter.setOnItemClickListener(this)
        mAdapter.setNewInstance(MethodDatas.getFunctionDescribe())

    }

    override fun initObserve(): Nothing? = null

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val item = mAdapter.getItem(position)
        if (item.name == "Demo4Fragment") {
            Navigation.findNavController(view).navigate(R.id.action_nav_demo4_fragment)
        } else {
            item.cls?.let { startActivity(Intent(requireActivity(), it)) }
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

    override fun onDestroyView() {
        super.onDestroyView()
        mDispatcherEnabled = false
    }

}