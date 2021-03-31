package com.base.library.mvvm.core

import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.base.library.base.BActivity
import java.lang.reflect.ParameterizedType

/**
 * MVVM 模式的基础 Activity
 */
abstract class VMActivity<VM : BViewModel, VB : ViewBinding> : BActivity() {

    lateinit var viewModel: VM
    lateinit var viewBinding: VB

    override fun initParadigm() {
        super.initParadigm()
        viewBinding = getVbClass()
        viewModel = ViewModelProvider(this).get(getVmClass())
        viewModel.getState()?.observe(this, Observer { it.handler(this) })
    }

    private fun <VM> getVmClass(): VM {
        return (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as VM
    }

    private fun <VB> getVbClass(): VB {
        val type = javaClass.genericSuperclass as ParameterizedType
        val clazz = type.actualTypeArguments[1] as Class<VB>
        return clazz.getMethod("inflate", LayoutInflater::class.java)
            .invoke(null, layoutInflater) as VB
    }

}
