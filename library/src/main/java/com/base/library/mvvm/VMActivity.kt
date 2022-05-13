package com.base.library.mvvm

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.base.library.base.BActivity
import com.dylanc.viewbinding.base.ViewBindingUtil

/**
 * MVVM 模式的基础 Activity
 */
abstract class VMActivity<VB : ViewBinding> : BActivity() {

    lateinit var viewBinding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ViewBindingUtil.inflateWithGeneric(this, layoutInflater)
        super.onCreate(savedInstanceState)
    }

}
