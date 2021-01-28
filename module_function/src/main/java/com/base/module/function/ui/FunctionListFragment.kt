package com.base.module.function.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.base.library.base.BFragment
import com.base.module.common.module_function.FunctionARoute
import com.base.module.function.R
import com.base.module.function.adapter.FunctionAdapter
import com.base.module.function.databinding.FragmentFunctionListBinding
import com.base.module.function.utils.FunctionMethod
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@Route(path = FunctionARoute.Function_FunctionListFragment)
@AndroidEntryPoint
class FunctionListFragment : BFragment(), OnItemClickListener {

    private val mBind by lazy { FragmentFunctionListBinding.inflate(layoutInflater) }

    @Inject
    lateinit var mAdapter: FunctionAdapter

    /**
     * callback 赋值的时候,回调里面拦截返回事件,这是首页返回给出提示是否直接退出
     * onDestroyView 跳转其它页面的时候remove回调,其它页面返回的时候不受影响
     */
    private var callback: OnBackPressedCallback? = null

    private var demo1Launch: ActivityResultLauncher<Intent>? = null

    override fun initArgs(mArguments: Bundle?) {
    }

    override fun initView() {
        setContentViewBar(mBind.root, false)
    }

    override fun initData(savedInstanceState: Bundle?) {
        registerDemo1Result()

        callback = requireActivity().onBackPressedDispatcher.addCallback {
            isEnabled = true
            LogUtils.d("返回事件被我拦截了，已经在首页了")
        }

        mBind.functionFragmentRv.layoutManager = LinearLayoutManager(requireActivity())
        mBind.functionFragmentRv.adapter = mAdapter

        mAdapter.animationEnable = true
        mAdapter.setOnItemClickListener(this)
        mAdapter.setNewInstance(FunctionMethod.getFunctionDescribe())
    }

    override fun initObserve(): Nothing? = null

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val item = mAdapter.getItem(position)

        when (item.name) {
            "Demo1" -> item.cls?.let { demo1Launch?.launch(Intent(requireActivity(), it)) }
            "Demo3" -> findNavController().navigate(R.id.action_function_demo3)
            "Demo4" -> findNavController().navigate(R.id.action_function_demo4)
            "协程" -> findNavController().navigate(R.id.action_function_coroutines)
            "异步流" -> findNavController().navigate(R.id.action_function_flow)
        }
    }

    private fun registerDemo1Result() {
        demo1Launch = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            LogUtils.d(it.resultCode)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        callback?.remove()
    }

}