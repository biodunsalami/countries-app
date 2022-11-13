package com.example.countriesapplication

class Repository(private val apiService: ApiService) {

    suspend fun getCountries(): List<MyCountry> {
        val apiCountriesList = apiService.getCountries().map {
            it.myCountry
        }
        return apiCountriesList
    }
}