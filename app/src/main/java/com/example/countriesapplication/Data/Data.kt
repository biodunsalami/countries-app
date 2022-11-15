package com.example.countriesapplication.Data

import com.example.countriesapplication.models.myModels.FilterModel

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
        FilterModel.Member("Africa", null),
        FilterModel.Member("Antarctica", null),
        FilterModel.Member("Asia", null),
        FilterModel.Member("Australia", null),
        FilterModel.Member("Europe", null),
        FilterModel.Member("North America", null),
        FilterModel.Member("South America", null)
    )

    val timeZones = arrayListOf(
        FilterModel.Member("GMT+1:00", null),
        FilterModel.Member("GMT+2:00", null),
        FilterModel.Member("GMT+3:00", null),
        FilterModel.Member("GMT+4:00", null),
        FilterModel.Member("GMT+5:00", null)
    )

    val continent = ArrayList<FilterModel.Member>()
    val timeZone = ArrayList<FilterModel.Member>()


    val allModels = arrayListOf(
        FilterModel("Region", continents),
        FilterModel("TimeZone", timeZones)
    )


}