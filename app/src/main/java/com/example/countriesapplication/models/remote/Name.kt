package com.example.countriesapplication.models.remote

data class Name(
    val common: String = "",
    val nativeName: com.example.countriesapplication.models.remote.NativeName? = null,
    val official: String
)