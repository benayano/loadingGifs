package com.example.loadinggifs.model.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GifApiService {
    @GET("gifs/trending")
    fun fetchTrendingGifs(@Query("api_key")apiKey:String,@Query("limit")limit:Int =20):Call<GifResponse>
}