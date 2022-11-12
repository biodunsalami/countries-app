package com.example.countriesapplication

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Dispatcher


class Repository(private val apiService: ApiService) {


    suspend fun getCountries(): ArrayList<MyCountry> {

        val apiCountriesList = apiService.getCountries()

//        Log.e("API DATA", "$apiCountriesList")

        val countriesList = ArrayList<MyCountry>()


        for (country in apiCountriesList) {

            val myCountry = MyCountry(
                country.name?.common,
                country.capital,
                country.flag,
                country.population,
                country.languages,
                country.independent,
                country.area,
                country.currencies,
                country.timezones,
                country.idd?.root,
                country.car?.side,
                country.flags,
                country.coatOfArms,
                country.continents
            )

            countriesList.add(myCountry)

        }

        return countriesList

    }



}