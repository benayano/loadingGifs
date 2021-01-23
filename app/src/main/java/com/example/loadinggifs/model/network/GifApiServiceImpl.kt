package com.example.loadinggifs.model.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit

object GifApiServiceImpl {

    val service: GifApiService by lazy { getRetroFit().create(GifApiService::class.java) }

    private fun getRetroFit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl("https://api.giphy.com/v1/")
            .addConverterFactory(
                Json { ignoreUnknownKeys = true }
                    .asConverterFactory(MediaType.get("application/json")))
            .build()
    }
}