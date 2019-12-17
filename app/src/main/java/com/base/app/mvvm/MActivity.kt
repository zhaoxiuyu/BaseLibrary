package com.base.app.mvvm

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.base.library.base.BaseActivity
import com.base.library.http.BManager
import com.base.library.mvp.BasePresenter
import java.lang.reflect.ParameterizedType

abstract class MActivity<VM : BaseViewModel> : BaseActivity<BasePresenter>() {

    var viewModel: VM? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObserve()
    }

    private fun initObserve() {
        viewModel?.setBView(this)

    }

    fun createViewModel() {
        val type = javaClass.genericSuperclass // 获得到的是类的泛型的类型
        val modelClass = if (type is ParameterizedType) {
            type.actualTypeArguments[0] as Class<*>
        } else {
            //如果没有指定泛型参数，则默认使用BaseViewModel
            BaseViewModel::class.java
        }
        viewModel = ViewModelProviders.of(this).get(modelClass as Class<VM>)
    }

}