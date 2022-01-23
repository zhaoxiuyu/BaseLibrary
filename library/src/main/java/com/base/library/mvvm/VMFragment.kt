package com.base.library.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        initViewModel()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun initViewModel() {
        // 使用Activity初始化ViewModel,就不用注册弹窗状态监听了,因为Activity已经注册过了
        if (getUseAct()) {
            viewModel = ViewModelProvider(requireActivity())[getVmClass()]
        } else {
            viewModel = ViewModelProvider(this)[getVmClass()]
            viewModel.getUIChangeLiveData().observe(viewLifecycleOwner) { it.handler(this) }
        }
    }

    // 如果是同一个ViewModel想使用Activity初始化，请重写这个方法并且返回true
    open fun getUseAct(): Boolean {
        return false
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