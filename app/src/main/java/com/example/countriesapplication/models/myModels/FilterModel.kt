package com.example.countriesapplication

data class FilterModel(
    val filterCategoryTitle: String,
    val nestedList: ArrayList<Member>
){
    data class Member(
        val title: String,
        val isChecked: Boolean? = null
    )
}

