package com.example.countriesapplication.models.myModels

import java.io.Serializable


data class MyCountry(
    val name: String?,
    val capital: List<String>?,
    val logo: String,
    val population: Int,
    val officialLanguage: com.example.countriesapplication.models.remote.Languages? = null,
    val independent: Boolean? = null,
    val area: Double,
    val currency: com.example.countriesapplication.models.remote.Currencies? = null,
    val timeZone: List<String>?,
    val diallingCode: String?,
    val drivingSide: String?,
    val flag: com.example.countriesapplication.models.remote.Flags?,
    val coatOfArms: com.example.countriesapplication.models.remote.CoatOfArms? = null,
    val continents: List<String>? = null,
    val landlocked: Boolean? = null,
    val unMember: Boolean? = null,
    val startOfWeek: String?
) : Serializable, CountryListItem()


sealed class CountryListItem
data class CountryHeader(val header: Char) : CountryListItem()
