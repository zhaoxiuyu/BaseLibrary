package com.base.library.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.base.library.base.BFragment
import com.dylanc.viewbinding.base.ViewBindingUtil
import kotlinx.coroutines.launch

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

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

}