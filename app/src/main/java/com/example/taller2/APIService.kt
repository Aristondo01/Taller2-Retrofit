package com.example.taller2

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface APIService {

    @GET("top-headlines")
    suspend fun getNewsBYCategorys(
        @Query("country") country:String,
        @Query("category") category:String,
        @Query("apiKey") apiKey:String
        ):Response<NewsResponse>

}