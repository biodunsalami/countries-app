package com.example.countriesapplication.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.countriesapplication.R
import com.example.countriesapplication.databinding.FragmentCountryDetailsBinding


class CountryDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCountryDetailsBinding

    private val args : CountryDetailsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCountryDetailsBinding.inflate(inflater, container, false)

        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.backArrow.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.countryName.text = args.selectedCountry.name
        binding.population.text = args.selectedCountry.population.toString()
        binding.region.text = args.selectedCountry.continents?.get(0) ?: ""
        binding.capital.text = args.selectedCountry.capital?.get(0) ?: ""

//        binding.officialLanguage.text = args.selectedCountry.officialLanguage.
        binding.ethnicGroup.visibility = View.INVISIBLE
        binding.ethnicGroupLabel.visibility = View.INVISIBLE

        binding.landLocked.text = args.selectedCountry.landlocked.toString()

        binding.independence.text = args.selectedCountry.independent.toString()
        binding.area.text = args.selectedCountry.area.toString()
//        binding.currency.text = args.selectedCountry.currency

        binding.timeZone.text = args.selectedCountry.timeZone.toString()



        binding.diallingCode.text = args.selectedCountry.diallingCode
        binding.drivingSide.text = args.selectedCountry.drivingSide

        binding.unMember.text = args.selectedCountry.unMember.toString()
        binding.startOfWeek.text = args.selectedCountry.startOfWeek


    }




}