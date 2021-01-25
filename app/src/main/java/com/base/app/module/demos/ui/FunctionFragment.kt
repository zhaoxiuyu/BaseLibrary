package com.base.app.module.demos.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.IdRes
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.base.app.R
import com.base.app.base.MyConstant
import com.base.app.databinding.FragmentFunctionBinding
import com.base.app.module.common.ui.MainActivity
import com.base.app.module.common.viewmodel.MainViewModel
import com.base.app.module.demos.adapter.FragmentAdapter
import com.base.app.utils.MethodDatas
import com.base.library.base.BFragment
import com.base.library.mvvm.core.BViewModel
import com.base.library.mvvm.template.repository.Demo4Repository
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * 其他测试 列表
 */
@AndroidEntryPoint
class FunctionFragment : BFragment(), OnItemClickListener {

    private val mMainViewModel: MainViewModel by activityViewModels()

    private val mBind by lazy { FragmentFunctionBinding.inflate(layoutInflater) }

    private var param1: String? = null
    private var param2: String? = null

//    private val mAdapter by lazy { FragmentAdapter() }

    @Inject
    lateinit var mAdapter: FragmentAdapter

    private var demo1Launch: ActivityResultLauncher<Intent>? = null

    /**
     * callback 赋值的时候,回调里面拦截返回事件,这是首页返回给出提示是否直接退出
     * onDestroyView 跳转其它页面的时候remove回调,其它页面返回的时候不受影响
     */
    private var callback: OnBackPressedCallback? = null

    override fun initArgs(mArguments: Bundle?) {
        mArguments?.let {
            param1 = it.getString(MyConstant.ARG_PARAM1)
            param2 = it.getString(MyConstant.ARG_PARAM2)
            LogUtils.d(mArguments.getString("topBarTitle"))
        }
    }

    override fun initView() {
        setContentViewBar(mBind.root, false)
        setTitleBarOperation("测试功能")
        getTitleBar().leftIcon = null
    }

    override fun initData(savedInstanceState: Bundle?) {
        mMainViewModel.bnvShowLiveData.value = true

        registerDemo1Result()

        callback = requireActivity().onBackPressedDispatcher.addCallback {
            isEnabled = true
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

    override fun initObserve(): MutableList<BViewModel>? {
        return null
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val item = mAdapter.getItem(position)
        when (item.name) {
            "Demo3Activity" -> {
                navigate(R.id.action_function_to_demo3)
            }
            "Demo4Fragment" -> {
                navigate(R.id.action_function_to_demo4)
            }
            "协程" -> {
                navigate(R.id.action_function_to_coroutines)
            }
            "异步流" -> {
                navigate(R.id.action_function_to_flow)
            }
            else -> {
                item.cls?.let { demo1Launch?.launch(Intent(requireActivity(), it)) }
            }
        }
    }

    private fun registerDemo1Result() {
        demo1Launch = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            LogUtils.d(it.resultCode)
        }
    }

    private fun navigate(@IdRes resId: Int) {
        findNavController().navigate(resId)
        mMainViewModel.bnvShowLiveData.value = false
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
        callback?.remove()
    }

}