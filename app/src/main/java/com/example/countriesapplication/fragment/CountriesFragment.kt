package com.example.countriesapplication.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.countriesapplication.Api
import com.example.countriesapplication.R
import com.example.countriesapplication.Repository
import com.example.countriesapplication.databinding.FragmentCountriesBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class CountriesFragment : Fragment() {

    private lateinit var binding: FragmentCountriesBinding

    private val repository = Repository(Api.apiService)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCountriesBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.languageSelect.setOnClickListener {
            val languageBottomSheetDialog = BottomSheetDialog(
                requireContext(),
                R.style.BottomSheetDialogTheme
            )

//            val bottomSheetView = LayoutInflater.from(requireContext()).inflate(
//                R.layout.language_bottom_sheet,
//            )
        }



        MainScope().launch {
            val countries = repository.getCountries()
//            binding.dogImage.load(dog.message)
            Log.e("XXX", "$countries")
        }

    }


}