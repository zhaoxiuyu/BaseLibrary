package com.base.library.mvvm.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.base.library.base.BFragment
import java.lang.reflect.ParameterizedType

abstract class VMFragment<VM : BViewModel, VB : ViewBinding> : BFragment() {

    lateinit var viewModel: VM
    lateinit var viewBinding: VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = getVbClass(container)

        viewModel = ViewModelProvider(this).get(getVmClass())
        viewModel.getState()?.observe(viewLifecycleOwner, Observer { it.handler(this) })

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun <VM> getVmClass(): VM {
        return (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as VM
    }

    private fun <VB> getVbClass(container: ViewGroup?): VB {
        val type = javaClass.genericSuperclass as ParameterizedType
        val clazz = type.actualTypeArguments[1] as Class<VB>
        return clazz.getMethod(
            "inflate", LayoutInflater::class.java,
            ViewGroup::class.java, Boolean::class.java
        ).invoke(null, layoutInflater, container, false) as VB
    }

}