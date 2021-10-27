package com.base.app.module.test

import android.content.Intent
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.base.app.databinding.DetailsBinding
import com.base.library.mvvm.VMActivity
import com.base.module.common.module_app.AppARoute

/**
 * 测试用的
 */
@Route(path = AppARoute.App_DetailActivity)
class DetailActivity : VMActivity<DetailViewModel, DetailsBinding>() {

//    private val mBind by lazy { DetailsBinding.inflate(layoutInflater) }
//    private val mViewModel: DetailViewModel by viewModels()
//    private val mViewModel by lazy { ViewModelProvider(this).get(DetailViewModel::class.java) }

    override fun initArgs(mIntent: Intent?) {
    }

    override fun initView() {
        // 给 ContentView 的外面添加一个 通用的顶部导航栏,可以设置第二个参数是否沉浸式,默认是true
        setContentViewBar(viewBinding.root, topPadding = viewBinding.root)
        // 通过getTitleBar()来操作导航栏
//        getTitleBar().title = "标题"
    }

    override fun initData(savedInstanceState: Bundle?) {
        viewBinding.tv.text = "设置一下内容"
        viewBinding.tv.setOnClickListener {
            viewModel.getArticle2().observe(this, {
                viewBinding.tv.text = it.showMsg()
            })
        }
    }

    override fun registerObserve() {
    }

}
