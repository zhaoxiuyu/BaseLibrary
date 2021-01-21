package com.base.app.module.views.imageview.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.base.app.databinding.FragmentDynamicBinding
import com.base.library.base.BFragment
import com.base.library.interfaces.MyTitleBarListener

/**
 * 可设置宽高比的图片
 */
class DynamicFragment : BFragment() {

    private val mBind by lazy { FragmentDynamicBinding.inflate(layoutInflater) }

    override fun initArgs(mArguments: Bundle?) {}

    override fun initView() {
        setContentViewBar(mBind.root)
    }

    override fun initData(savedInstanceState: Bundle?) {
        setTitleBarOperation("可设置宽高比的图片", object : MyTitleBarListener() {
            override fun onLeftClick(v: View?) {
                findNavController().navigateUp()
            }
        })

    }

    override fun initObserve(): Nothing? = null

}