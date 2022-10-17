package com.base.function.ui.fragment

import android.os.Bundle
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.base.common.constant.CommonARoute
import com.base.common.entitys.PageDescribe
import com.base.function.R
import com.base.function.constant.FunctionMethod
import com.base.function.databinding.FunctionFragmentListBinding
import com.base.library.mvvm.VMFragment
import com.blankj.utilcode.util.LogUtils
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup

@Route(path = CommonARoute.Function_FunctionListFragment)
class FunctionListFragment : VMFragment<FunctionFragmentListBinding>() {

    /**
     * callback 赋值的时候,回调里面拦截返回事件,这是首页返回给出提示是否直接退出
     * onDestroyView 跳转其它页面的时候remove回调,其它页面返回的时候不受影响
     */
    private var callback: OnBackPressedCallback? = null


    override fun initArgs(mArguments: Bundle?) {
    }

    override fun initData(savedInstanceState: Bundle?) {
        addStatusBarTopPadding(viewBinding.root)
        initAdapter()

        viewBinding.butCollapse.setOnClickListener {
            throw  RuntimeException("Boom!")
        }

        callback = requireActivity().onBackPressedDispatcher.addCallback {
            isEnabled = true
            LogUtils.d("返回事件被我拦截了，已经在首页了")
        }
    }

    override fun registerObserve() {
    }

    var demo1Launch =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            LogUtils.d(it.resultCode)
        }
    var demo5Launch = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        LogUtils.d(it.resultCode)
    }

    private fun initAdapter() {
        viewBinding.functionFragmentRv.linear().setup {
            addType<PageDescribe>(R.layout.function_fragment_item)
            onBind {
                val model = getModel<PageDescribe>()
                findView<TextView>(R.id.material_textview_name).text = model.name
                findView<TextView>(R.id.material_textview_describe).text = model.name
            }
            onClick(R.id.itemView) {
                val model = getModel<PageDescribe>()
                when (model.name) {
                    "Demo1", "Demo4" -> model.path?.let { path ->
                        ARouter.getInstance().build(path).navigation()
                    }
                    "协程" -> findNavController().navigate(R.id.action_function_coroutines)
                    "异步流" -> findNavController().navigate(R.id.action_function_flow)
                }
            }
        }.models = FunctionMethod.getFunctionDescribe()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        callback?.remove()
    }

}