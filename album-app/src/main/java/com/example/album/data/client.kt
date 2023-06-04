package com.example.album.data

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

const val API_URL = "https://jsonplaceholder.typicode.com/"

fun getClient() = Retrofit.Builder()
    .client(
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            ).build()
    )
    .addConverterFactory(GsonConverterFactory.create(Gson()))
    .baseUrl(API_URL)
    .build().create<Api>()
