package com.example.countriesapplication

object Data {

    val alphabets = arrayListOf(
        'A',
        'B',
        'C',
        'D',
        'E',
        'F',
        'G',
        'H',
        'I',
        'J',
        'K',
        'L',
        'M',
        'N',
        'O',
        'P',
        'Q',
        'R',
        'S',
        'T',
        'U',
        'V',
        'W',
        'X',
        'Y',
        'Z'
    )

    val filterCategories = arrayListOf("Continent", "Timezones")

    val continents = arrayListOf(
        "Africa",
        "Antarctica",
        "Asia",
        "Australia",
        "Europe",
        "North America",
        "South America"
    )

    val timeZones = arrayListOf("GMT+1:00", "GMT+2:00", "GMT+3:00", "GMT+4:00", "GMT+5:00")

    val nestedList = arrayListOf(
        FilterModel("Continent", continents),
        FilterModel("Timezones", timeZones)
    )


}