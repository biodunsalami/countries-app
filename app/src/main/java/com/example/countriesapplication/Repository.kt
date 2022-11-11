package com.example.countriesapplication

import com.example.countriesapplication.models.CountriesResponse
import com.example.countriesapplication.models.CountriesResponseItem

class Repository(private val apiService: ApiService) {

//    val dog = DogResponse("", "")

    suspend fun getCountries(): List<CountriesResponseItem> {
        return apiService.getCountries()
    }

}