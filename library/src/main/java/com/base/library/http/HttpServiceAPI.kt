package com.base.library.http

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

interface HttpServiceAPI {

    /**
     * 通用的请求接口
     */
    @POST("api/{code}")
    fun api(@Path("code") code: String, @Body param: String): Observable<String>

    @GET("banner/json")
    fun apiPay(@Body param: String): Observable<String>

    @POST("banner/json")
    fun getPostsMap(@QueryMap params: Map<String, String>): Call<String>

    /**
     * 通用的请求接口
     * Header：urlName默认default,使用默认的baseUrl。否则在拦截器里面使用你传进来的值作为BaseUrl
     */
    @GET("banner/json")
    fun apiUrl(@Header("urlName") baseUrl: String = "default"): Observable<String>

    /**
     * 协程
     */
    @POST("banner/json")
    suspend fun getPostsAsync(): String

}