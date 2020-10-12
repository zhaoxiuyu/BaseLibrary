package com.base.app.base

import com.base.library.base.BApplication
import com.base.library.util.CockroachUtil
import com.blankj.utilcode.util.LogUtils
import com.didichuxing.doraemonkit.DoraemonKit

class MyApp : BApplication() {

    override fun onCreate() {
        super.onCreate()

        initRxHttp()
//        initCockroach()
        DoraemonKit.install(this, "0f0894d53fe597a618cb4e0c31e2f536")
    }

    /**
     * todo 不死异常拦截
     * handlerException内部建议手动try{ 异常处理逻辑 }catch(Throwable e){ }
     * 以防handlerException内部再次抛出异常，导致循环调用handlerException
     */
    private fun initCockroach() {
        CockroachUtil.install(object : CockroachUtil.ExceptionHandler {
            override fun handlerException(thread: Thread, throwable: Throwable, info: String) {
                try {
                    LogUtils.e(info)
                } catch (e: Throwable) {
                }
            }
        })
    }

}