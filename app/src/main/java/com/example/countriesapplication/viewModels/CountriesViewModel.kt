package com.example.countriesapplication.viewModels

import androidx.lifecycle.*
import com.example.countriesapplication.MyCountry
import com.example.countriesapplication.Repository
import kotlinx.coroutines.launch

class CountriesViewModel(private val repository: Repository): ViewModel() {

    private val _myResponse: MutableLiveData<List<MyCountry>> by lazy {
        MutableLiveData(null)
    }
    val myResponse: LiveData<List<MyCountry>>
        get() = _myResponse


    var normalList = ArrayList<MyCountry>()


    fun getCountry(){
        viewModelScope.launch {
            try {
                val response = repository.getCountries()
                _myResponse.value = response
                normalList = response
            }catch (e: Exception){
                //Handle it
            }
        }
    }

    fun getFilteredByAlphabets(): List<MyCountry>?{
//        return _myResponse.value?.filter { country -> country.name?.get(0) == 'A' }
        getCountry()
        return normalList.filter { country -> country.name?.get(0) == 'A' }
    }




//    suspend fun getCountries(): List<MyCountry>{
//        return repository.getCountries()
//    }



}
