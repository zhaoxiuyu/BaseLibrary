package com.base.module.function.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.base.library.base.BConstant
import com.base.library.entitys.BResponse
import com.base.library.entitys.response.WanArticle
import com.base.library.entitys.response.WanChapters
import com.base.library.entitys.response.WanLogin
import com.base.library.mvvm.BViewModel
import com.base.module.function.mvvm.repository.Demo4Repository
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * 使用 ViewModelInject ，数据仓库要在构造方法里面初始化
 */
class Demo4ViewModel : BViewModel() {

    private val m4Repository by lazy { Demo4Repository() }

    /**
     * 如果变量声明方式初始化，ViewModel需要用@ActivityRetainedScoped,ViewModel实例也需要用注解
     */
//    @Inject
//    lateinit var mRepository: Demo4Repository

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
        viewModelScopeLoadDisMess(BConstant.login) {
            val mLogin = m4Repository.getLogin(username, password)
            if (mLogin.isSuccess()) {
                loginLiveData.value = mLogin
            } else {
                messageEvent(BConstant.login, mLogin.showMsg())
            }
        }
    }

    /**
     * 登录 -> 首页banner。串行
     */
    fun getLoginBanner(username: String, password: String) = liveData {
        flow {
            // 登录
            val login = m4Repository.getLogin(username, password)

            // 首页banner
            val banner = m4Repository.getBanner()

            emit(Pair(login, banner))
        }.onStart { loadingEvent() }.onCompletion { dismissEvent() }.collectLatest { emit(it) }
    }

    /**
     * 公众号 文章列表并行获取
     */
    private val parallelLiveData =
        MutableLiveData<Pair<BResponse<MutableList<WanChapters>>, BResponse<WanArticle>>>()

    fun getParallelLiveData(): LiveData<Pair<BResponse<MutableList<WanChapters>>, BResponse<WanArticle>>> =
        parallelLiveData

    fun getParallel() {
        viewModelScopeLoadDis {
            // 公众号列表
            val chapters = m4Repository.getChapters(this)

            // 首页文章列表
            val article = m4Repository.getArticle(this)

            val chaptersAwait = chapters.await()
            val articleNewAwait = article.await()
            if (chaptersAwait.isSuccess() && articleNewAwait.isSuccess()) {
                parallelLiveData.value = Pair(chaptersAwait, articleNewAwait)
            } else {
                val msg = "${chaptersAwait.showMsg()},${articleNewAwait.showMsg()}"
                messageEvent(BConstant.login, msg)
            }
        }
    }

    /**
     * liveData{ }协程代码块,产生一个不可变LiveData
     * emit()方法用来更新LiveData数据
     * collectLatest末端操作符,一段时间内只接受最新的发射过来的数据
     */
    fun getCache(key: String) = liveData<String> {
        m4Repository.getCacheFlow(key)
            .catch { LogUtils.e("出异常了 = ${it.message}") }
            .onStart { }
            .onCompletion { }
            .collectLatest { emit(it) }
    }

    /**
     * 添加缓存
     */
    fun putCache(key: String, content: String) {
        viewModelScope(
            block = {
                launch(Dispatchers.IO) { m4Repository.putCacheFlow(key, content) }
            }, onFinally = {
                LogUtils.d("添加缓存 key : $key")
            })
    }

}