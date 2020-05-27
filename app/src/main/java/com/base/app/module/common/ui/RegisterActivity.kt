package com.base.app.module.common.ui

import android.content.Intent
import androidx.lifecycle.Observer
import com.base.app.R
import com.base.app.module.common.viewmodel.RegisterViewModel
import com.base.library.entitys.BResponse
import com.base.library.entitys.response.Chapters
import com.base.library.mvvm.core.VMActivity
import kotlinx.android.synthetic.main.activity_register.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : VMActivity() {

    private val vm by viewModel<RegisterViewModel>()

    private val sb = StringBuilder("")

    override fun initArgs(intent: Intent?) {
    }

    override fun initView() {
        initContentView(R.layout.activity_register)
    }

    override fun initData() {
        but1.setOnClickListener { vm.getChapters() }
        but2.setOnClickListener { vm.getBanner() }

        vm.liveChapters.observe(this, Observer { br ->
            sb.delete(0, sb.length)
            br.data?.forEach { sb.appendln(it.name) }
            sb.appendln("liveChapters")
            tv.text = sb.toString()
        })
        vm.liveBanner.observe(this, Observer { it1 ->
            sb.delete(0, sb.length)
            it1.data?.forEach { sb.appendln(it.title) }
            sb.appendln("liveBanner")
            tv.text = sb.toString()
        })

        but3.setOnClickListener {
            vm.getHh().observe(this, Observer { it1 ->
                it1.handler(object : OnCallback<List<Chapters>>() {})
                sb.delete(0, sb.length)
                it1.data?.forEach { sb.appendln(it.title) }
                sb.appendln("getHh")
                tv.text = sb.toString()
            })
        }
    }

    override fun stateLiveData(bResponse: BResponse<Any>) {
        bResponse.handler(object : OnCallback<Any>() {
            override fun onLoading(msg: String) {
                super.onLoading("看看改变了没")
            }
        })
    }

}