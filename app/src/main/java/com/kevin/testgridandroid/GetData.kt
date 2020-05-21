package com.kevin.testgridandroid

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GetData {

    @GET("v2/list")
    fun getData(@Query("page") page: Int) : Observable<List<Data>>

}