package com.example.countriesapplication.models.remote

import com.example.countriesapplication.models.myModels.MyCountry


data class CountriesResponseItem(
    val altSpellings: List<String>? = null,
    val area: Double = 0.0,
    val borders: List<String>? = null,
    val capital: List<String>? = null,
    val capitalInfo: com.example.countriesapplication.models.remote.CapitalInfo? = null,
    val car: com.example.countriesapplication.models.remote.Car? = null,
    val cca2: String = "",
    val cca3: String = "",
    val ccn3: String = "",
    val cioc: String = "",
    val coatOfArms: com.example.countriesapplication.models.remote.CoatOfArms? = null,
    val continents: List<String>? = null,
    val currencies: com.example.countriesapplication.models.remote.Currencies? = null,
    val demonyms: com.example.countriesapplication.models.remote.Demonyms? = null,
    val fifa: String = "",
    val flag: String = "",
    val flags: com.example.countriesapplication.models.remote.Flags? = null,
    val gini: com.example.countriesapplication.models.remote.Gini? = null,
    val idd: com.example.countriesapplication.models.remote.Idd? = null,
    val independent: Boolean? = null,
    val landlocked: Boolean? = null,
    val languages: com.example.countriesapplication.models.remote.Languages? = null,
    val latlng: List<Double>? = null,
    val maps: com.example.countriesapplication.models.remote.Maps? = null,
    val name: com.example.countriesapplication.models.remote.Name? = null,
    val population: Int = 0,
    val postalCode: com.example.countriesapplication.models.remote.PostalCode? = null,
    val region: String = "",
    val startOfWeek: String = "",
    val status: String = "",
    val subregion: String = "",
    val timezones: List<String>? = null,
    val tld: List<String>? = null,
    val translations: com.example.countriesapplication.models.remote.Translations? = null,
    val unMember: Boolean? = null
) {
    val myCountry: MyCountry
        get() = MyCountry(
            name?.common,
            capital,
            flag,
            population,
            languages,
            independent,
            area,
            currencies,
            timezones,
            idd?.root,
            car?.side,
            flags,
            coatOfArms,
            continents,
            landlocked,
            unMember,
            startOfWeek
        )
}