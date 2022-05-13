package com.base.app.http

import com.base.app.entitys.response.WanAndroid
import com.base.app.entitys.response.WanArticle
import com.base.app.entitys.response.WanChapters
import com.base.app.entitys.response.WanLogin
import com.base.library.base.BResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface OkHttpApi {

    /**
     * suspend
     */

    // 登录
    @FormUrlEncoded
    @POST(OkHttpContracts.login)
    suspend fun getLogin(
        @Field("username") username: String,
        @Field("password") password: String
    ): BResponse<WanLogin>

    // 登录
    @FormUrlEncoded
    @POST(OkHttpContracts.login)
    suspend fun getLoginStr(
        @Field("username") username: String,
        @Field("password") password: String
    ): String

    // 首页banner
    @GET(OkHttpContracts.banner)
    suspend fun getBanner(): BResponse<MutableList<WanAndroid>>

    // 首页文章列表
    @GET(OkHttpContracts.article)
    suspend fun getArticle(): BResponse<WanArticle>

    // 公众号列表
    @GET(OkHttpContracts.chapters)
    suspend fun getChapters(): BResponse<MutableList<WanChapters>>

    /**
     * Observable
     */

    // 登录
    @FormUrlEncoded
    @POST(OkHttpContracts.login)
    fun getLoginObser(
        @Field("username") username: String,
        @Field("password") password: String
    ): Observable<BResponse<WanLogin>>

    // 首页文章列表
    @GET(OkHttpContracts.article)
    fun getArticleObser(): Observable<BResponse<WanArticle>>

    // 公众号列表
    @GET(OkHttpContracts.chapters)
    fun getChaptersObser(): Observable<BResponse<MutableList<WanChapters>>>

}