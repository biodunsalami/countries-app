package com.example.countriesapplication.models

import com.example.countriesapplication.MyCountry


data class CountriesResponseItem(
    val altSpellings: List<String>? = null,
    val area: Double = 0.0,
    val borders: List<String>? = null,
    val capital: List<String>? = null,
    val capitalInfo: CapitalInfo? = null,
    val car: Car? = null,
    val cca2: String = "",
    val cca3: String = "",
    val ccn3: String = "",
    val cioc: String = "",
    val coatOfArms: CoatOfArms? = null,
    val continents: List<String>? = null,
    val currencies: Currencies? = null,
    val demonyms: Demonyms? = null,
    val fifa: String = "",
    val flag: String = "",
    val flags: Flags? = null,
    val gini: Gini? = null,
    val idd: Idd? = null,
    val independent: Boolean? = null,
    val landlocked: Boolean? = null,
    val languages: Languages? = null,
    val latlng: List<Double>? = null,
    val maps: Maps? = null,
    val name: Name? = null,
    val population: Int = 0,
    val postalCode: PostalCode? = null,
    val region: String = "",
    val startOfWeek: String = "",
    val status: String = "",
    val subregion: String = "",
    val timezones: List<String>? = null,
    val tld: List<String>? = null,
    val translations: Translations? = null,
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