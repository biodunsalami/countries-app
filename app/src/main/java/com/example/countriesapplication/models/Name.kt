package com.example.countriesapplication.models

data class Name(
    val common: String = "",
    val nativeName: NativeName? = null,
    val official: String
)