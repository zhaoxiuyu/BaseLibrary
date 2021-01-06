package com.base.app.module.test

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.base.app.databinding.DetailsBinding
import com.base.library.base.BFragment
import com.base.library.mvvm.core.BViewModel

class DetailActivity : BFragment() {

    private val mBind by lazy { DetailsBinding.inflate(layoutInflater) }
    private val mViewModel by lazy { ViewModelProvider(this).get(DetailViewModel::class.java) }

    override fun initArgs(mArguments: Bundle?) {
    }

    override fun initView() {
        // 设置Activity的布局
        setContentView(mBind.root)

        // 给 ContentView 的外面添加一个 通用的顶部导航栏,可以设置第二个参数是否沉浸式,默认是true
        setContentViewBar(mBind.root)
        // 通过getTitleBar()来操作导航栏
        getTitleBar().title = "标题"

    }

    override fun initData(savedInstanceState: Bundle?) {
        mBind.tv.text = "设置一下内容"
        mBind.tv.setOnClickListener {
            mViewModel.getArticle()
        }
    }

    override fun initObserve(): MutableList<BViewModel>? {
        mViewModel.articleLiveData.observe(this, Observer {

        })
        return mutableListOf(mViewModel)
    }

}