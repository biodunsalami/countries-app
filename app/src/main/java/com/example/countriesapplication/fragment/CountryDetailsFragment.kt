package com.example.countriesapplication.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.example.countriesapplication.*
import com.example.countriesapplication.adapter.BaseRecyclerAdapter
import com.example.countriesapplication.databinding.FragmentCountryDetailsBinding
import com.example.countriesapplication.databinding.ItemImageRecyclerBinding


class CountryDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCountryDetailsBinding

    private val args: CountryDetailsFragmentArgs by navArgs()

    private lateinit var viewPager: ViewPager2


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







        val viewPagerAdapter = BaseRecyclerAdapter<String?>()

        val images = arrayListOf(args.selectedCountry.flag?.png, args.selectedCountry.coatOfArms?.png)

        viewPagerAdapter.listOfItems = images

        viewPagerAdapter.expressionOnGetItemViewType = { _ -> 0 }

        viewPagerAdapter.expressionViewHolderBinding = { item, viewBinding ->
            viewBinding as ItemImageRecyclerBinding

            viewBinding.imageView.load(item)

        }

        viewPagerAdapter.expressionOnCreateViewHolder = { viewGroup, viewType ->
            ItemImageRecyclerBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        }

        viewPager = binding.viewPager

        viewPager.adapter = viewPagerAdapter

        binding.indicator.setViewPager(viewPager)


        binding.arrowLeftFrame.setOnClickListener {
            viewPager.setCurrentItem(viewPager.currentItem - 1, true)
        }

        binding.arrowRightFrame.setOnClickListener {
            viewPager.setCurrentItem(viewPager.currentItem + 1, true)
        }

    }


}
