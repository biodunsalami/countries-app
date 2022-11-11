package com.example.countriesapplication

import com.example.countriesapplication.models.CountriesResponse
import com.example.countriesapplication.models.CountriesResponseItem
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private val BASEURL = "https://restcountries.com/v3.1/"

interface ApiService {

    @GET("all")
    suspend fun getCountries(): List<CountriesResponseItem>
}

val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

val retrofit: Retrofit = Retrofit.Builder()
    .addConverterFactory(
        MoshiConverterFactory
            .create(moshi)
    )
    .baseUrl(BASEURL)
    .build()

object Api {
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}