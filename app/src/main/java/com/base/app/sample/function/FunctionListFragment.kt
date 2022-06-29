package com.base.app.sample.function

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
import com.base.app.R
import com.base.app.base.MyARoute
import com.base.app.base.MyMethod
import com.base.app.databinding.FragmentFunctionListBinding
import com.base.library.mvvm.VMFragment
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener

@Route(path = MyARoute.Function_FunctionListFragment)
class FunctionListFragment : VMFragment<FragmentFunctionListBinding>(), OnItemClickListener {

    private val mAdapter by lazy { FunctionAdapter() }

    /**
     * callback 赋值的时候,回调里面拦截返回事件,这是首页返回给出提示是否直接退出
     * onDestroyView 跳转其它页面的时候remove回调,其它页面返回的时候不受影响
     */
    private var callback: OnBackPressedCallback? = null

    private var demo1Launch: ActivityResultLauncher<Intent>? = null
    private var detailLaunch: ActivityResultLauncher<Intent>? = null
    private var demo5Launch: ActivityResultLauncher<Intent>? = null

    override fun initArgs(mArguments: Bundle?) {
    }

    override fun initView() {
        setContentView(viewBinding.root, topPadding = viewBinding.ll)
    }

    override fun initData(savedInstanceState: Bundle?) {
        registerDemo1Result()
        registerDetailResult()
        registerDemo5Result()

        viewBinding.butCollapse.setOnClickListener {
            throw  RuntimeException("Boom!")
        }

        callback = requireActivity().onBackPressedDispatcher.addCallback {
            isEnabled = true
            LogUtils.d("返回事件被我拦截了，已经在首页了")
        }

        viewBinding.functionFragmentRv.layoutManager = LinearLayoutManager(requireActivity())
        viewBinding.functionFragmentRv.adapter = mAdapter

        mAdapter.animationEnable = true
        mAdapter.setOnItemClickListener(this)
        mAdapter.setNewInstance(MyMethod.getFunctionDescribe())
    }

    override fun registerObserve() {
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val item = mAdapter.getItem(position)
        when (item.name) {
            "Demo1" -> item.cls?.let { demo1Launch?.launch(Intent(requireActivity(), it)) }
            "Demo3" -> findNavController().navigate(R.id.action_function_demo3)
//            "Demo4" -> findNavController().navigate(R.id.action_function_demo4)
            "协程" -> findNavController().navigate(R.id.action_function_coroutines)
            "异步流" -> findNavController().navigate(R.id.action_function_flow)
            "Demo5" -> item.cls?.let { demo5Launch?.launch(Intent(requireActivity(), it)) }
            "Demo4" -> item.cls?.let { demo5Launch?.launch(Intent(requireActivity(), it)) }
        }
    }

    private fun registerDemo1Result() {
        demo1Launch = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            LogUtils.d(it.resultCode)
        }
    }

    private fun registerDemo5Result() {
        demo5Launch = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            LogUtils.d(it.resultCode)
        }
    }

    private fun registerDetailResult() {
        detailLaunch = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            LogUtils.d(it.resultCode)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        callback?.remove()
    }

}