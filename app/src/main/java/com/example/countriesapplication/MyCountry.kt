package com.example.countriesapplication

import com.example.countriesapplication.models.*
import java.io.Serializable


data class MyCountry(
    val name: String?,
    val capital: List<String>?,
    val logo: String,
    val population: Int,
    val officialLanguage: Languages? = null,
    val independent: Boolean? = null,
    val area: Double,
    val currency: Currencies? = null,
    val timeZone: List<String>?,
    val diallingCode: String?,
    val drivingSide: String?,
    val flag: Flags?,
    val coatOfArms: CoatOfArms? = null,
    val continents: List<String>? = null
) : Serializable
