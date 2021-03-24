package com.base.module.function.mvvm.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.rxLifeScope
import com.base.library.base.BConstant
import com.base.library.entitys.BResponse
import com.base.library.entitys.response.WanArticle
import com.base.library.entitys.response.WanChapters
import com.base.library.entitys.response.WanLogin
import com.base.library.mvvm.core.BViewModel
import com.base.library.rxhttp.ResponseState
import com.base.module.function.mvvm.repository.Demo4Repository
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

/**
 * 使用 ViewModelInject ，数据仓库要在构造方法里面初始化
 */
class Demo4ViewModel @ViewModelInject constructor(private val mRepository: Demo4Repository) :
    BViewModel() {

    /**
     * 如果变量声明方式初始化，ViewModel需要用@ActivityRetainedScoped,ViewModel实例也需要用注解
     */
//    @Inject
//    lateinit var mRepository: Demo4Repository

    /**
     * liveData{ }协程代码块,产生一个不可变LiveData
     * emit()方法用来更新LiveData数据
     * collectLatest末端操作符,一段时间内只接受最新的发射过来的数据
     */
    fun getCache(key: String) = liveData<String> {
        mRepository.getCacheFlow(key)
            .catch { LogUtils.e("出异常了 = ${it.message}") }
            .onStart { }
            .onCompletion { }
            .collectLatest { emit(it) }
    }

    /**
     * 登录
     */
    private val loginLiveData = MutableLiveData<BResponse<WanLogin>>()
    fun getLoginLiveData(): LiveData<BResponse<WanLogin>> = loginLiveData

    fun collectLogin(username: String, password: String) {
        if (StringUtils.isEmpty(username)) {
            ToastUtils.showShort("请输入用户名")
            return
        }
        if (StringUtils.isEmpty(password)) {
            ToastUtils.showShort("请输入密码")
            return
        }
        rxLifeScope.launch({
            val mLogin = mRepository.getLogin(username, password)
            if (mLogin.isSuccess()) {
                loginLiveData.value = mLogin
            } else {
                sendState(ResponseState.Error(BConstant.login, mLogin.showMsg()))
            }
        }, null, { sendState(ResponseState.Loading(BConstant.login)) },
            { sendState(ResponseState.Completed(BConstant.login)) })
    }

    /**
     * 登录 -> 首页banner
     */
    fun getLoginBanner(username: String, password: String) = liveData {
        mRepository.getLoginBanner(username, password)
            .onStart { sendState(ResponseState.Loading(BConstant.login)) }
            .onCompletion { sendState(ResponseState.Completed(BConstant.login)) }
            .collectLatest { emit(it) }
    }

    /**
     * 公众号 文章 列表同步获取
     */
    private val parallelLiveData =
        MutableLiveData<Pair<BResponse<MutableList<WanChapters>>, BResponse<WanArticle>>>()

    fun getParallelLiveData(): LiveData<Pair<BResponse<MutableList<WanChapters>>, BResponse<WanArticle>>> =
        parallelLiveData

    fun getParallel() {
        rxLifeScope.launch(
            {
                val pair = mRepository.getChaptersInfo(this)
                if (pair.first.isSuccess() && pair.second.isSuccess()) {
                    parallelLiveData.value = pair
                } else {
                    sendState(ResponseState.Error(BConstant.login, pair.first.showMsg()))
                }
            }, null, { sendState(ResponseState.Loading(BConstant.login)) },
            { sendState(ResponseState.Completed(BConstant.login)) })
    }

    /**
     * 协程 - 并行获取列表
     * 无论是串行还是并行，如果其中一个出现异常了，协程自动关闭 并自动结束请求，停止剩下的代码走异常回调
     * 如果要互不影响，可以使用onErrorReturn、onErrorReturnItem出异常时给出默认对象
     */
    fun getParallel2() {
        rxLifeScope.launch({
            // 串行请求
//            articleLiveData.value = getBanners(this).await()
//            chaptersLiveData.value = getStudents(this).await()

            // 并行请求
//            val asyncBanners = cRepository.getArticle(this)
//            val asyncStudents = cRepository.getChapters(this)

//            articleLiveData.value = asyncBanners.await()
//            chaptersLiveData.value = asyncStudents.await()
        }, {
            LogUtils.d("出现异常了")
        })
    }

}