package com.base.library.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.base.library.base.BFragment
import com.dylanc.viewbinding.base.ViewBindingUtil

abstract class VMFragment<VB : ViewBinding> : BFragment() {

    private var mBinding: VB? = null
    val viewBinding: VB get() = mBinding!!

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        mBundle: Bundle?
    ) {
        mBinding = ViewBindingUtil.inflateWithGeneric(this, inflater, container, false)
    }

    override fun getRootView(): View = mBinding?.root!!

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

}