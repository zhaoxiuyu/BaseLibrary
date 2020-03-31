package com.base.library.base

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

interface BServiceAPI {

    /**
     * 通用的请求接口
     */
    @POST("api/{code}")
    fun api(@Path("code") code: String, @Body param: String): Observable<String>

    /**
     * 通用的请求接口
     */
    @GET("banner/json")
    fun apiPay(@Body param: String): Observable<String>

    /**
     * 通用的请求接口
     */
    @GET("banner/json")
    fun apiPay(): Observable<String>

    /**
     * 协程
     */
    @POST("idcard/query")
    fun getPostsAsync(@QueryMap params: Map<String, String>): Call<String>

}