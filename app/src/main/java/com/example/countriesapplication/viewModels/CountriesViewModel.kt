package com.example.countriesapplication.viewModels

import android.util.Log
import androidx.lifecycle.*
import com.example.countriesapplication.*
import kotlinx.coroutines.launch

class CountriesViewModel(private val repository: Repository) : ViewModel() {

    private val _myResponse: MutableLiveData<List<CountryListItem>> by lazy {
        MutableLiveData(null)
    }
    val myResponse: LiveData<List<CountryListItem>>
        get() = _myResponse


    fun getCountry() {
        viewModelScope.launch {
            try {
                val response = insertHeaders(repository.getCountries())
                Log.e("Timber", response.toString())
                _myResponse.value = response
            } catch (e: Exception) {
                //Handle it
            }
        }
    }

    private fun insertHeaders(response: List<MyCountry>): List<CountryListItem> {
        val map: Map<Char, List<MyCountry>> = response.groupBy { it.name?.first() ?: ' ' }
        val result = mutableListOf<CountryListItem>()

        Data.alphabets.forEach { alphabet ->
            result.add(CountryHeader(alphabet))
            result.addAll(map[alphabet].orEmpty())
        }
        return result
    }


}
