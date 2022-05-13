package com.base.library.mvp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.base.library.base.BFragment
import com.dylanc.viewbinding.base.ViewBindingUtil

abstract class VPFragment<VB : ViewBinding> : BFragment() {

    private var mBinding: VB? = null
    val viewBinding: VB get() = mBinding!!

    abstract fun addObserverPresenter(): MutableList<VPPresenter>

    override fun initViewBinding(inflater: LayoutInflater,container: ViewGroup?,mBundle: Bundle?) {
        mBinding = ViewBindingUtil.inflateWithGeneric(this, inflater, container, false)
        addObserverPresenter().forEach { lifecycle.addObserver(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

    override fun registerObserve() {
    }

}