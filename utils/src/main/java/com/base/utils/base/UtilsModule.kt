package com.base.utils.base

import android.content.Context
import com.base.common.interfaces.AppAutoServiceInterface
import com.blankj.utilcode.util.LogUtils
import com.google.auto.service.AutoService

@AutoService(AppAutoServiceInterface::class)
class UtilsModule : AppAutoServiceInterface {

    override fun onCreate(base: Context?) {
        super.onCreate(base)
        LogUtils.d("Init UtilsModule")
    }

}