package com.base.library.http

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface BService {

    /**
     * 通用的请求接口
     */
    @POST("api/{code}")
    fun api(@Path("code") code: String, @Body param: String): Observable<String>

    /**
     * 通用的请求接口
     */
    @POST
    fun apiPay(@Body param: String): Observable<String>

}