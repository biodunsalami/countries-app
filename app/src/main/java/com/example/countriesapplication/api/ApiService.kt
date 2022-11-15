package com.example.countriesapplication

import com.example.countriesapplication.models.remote.CountriesResponseItem
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

private val BASEURL = "https://restcountries.com/v3.1/"

interface ApiService {

    @GET("all")
    suspend fun getCountries(): List<com.example.countriesapplication.models.remote.CountriesResponseItem>
}


val okHttpClient =
    OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()


val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

val retrofit: Retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASEURL)
    .client(okHttpClient)
    .build()

object Api {
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}