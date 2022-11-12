package com.example.countriesapplication.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.countriesapplication.*
import com.example.countriesapplication.adapter.BaseRecyclerAdapter
import com.example.countriesapplication.databinding.FragmentCountriesBinding
import com.example.countriesapplication.databinding.ItemCountryAlphabetParentRecyclerBinding
import com.example.countriesapplication.databinding.ItemCountryNestedRecyclerBinding
import com.example.countriesapplication.viewModels.CountriesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class CountriesFragment : Fragment() {

    private lateinit var binding: FragmentCountriesBinding

    private val repository = Repository(Api.apiService)

    lateinit var viewModel: CountriesViewModel


    val countriesList = ArrayList<MyCountry>()


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

        viewModel =
            ViewModelProvider(this, MyViewModelFactory(repository))[CountriesViewModel::class.java]

//        MainScope().launch {
//            countries = repository.getCountries()
////            Log.e("XXX", "${countries[0]}")
//        }

        viewModel.getCountry()



        viewModel.myResponse.observe(viewLifecycleOwner){
            if (it == null){
                return@observe
            }

            //Setup A countries RV
            val aAdapter = BaseRecyclerAdapter<MyCountry>()
            aAdapter.listOfItems = viewModel.myResponse.value?.filter {country -> country.name?.get(0) == 'A' }
            Log.e("LISX", "${aAdapter.listOfItems}")

            aAdapter.expressionViewHolderBinding = { item, viewBinding ->
                val binding = viewBinding as ItemCountryNestedRecyclerBinding

                binding.countryName.text = item.name
                binding.countryCapital.text = item.capital?.get(0)
                binding.countryFlag.load(item.flag)


                binding.countryInfo.setOnClickListener {
                    findNavController().navigate(CountriesFragmentDirections.actionCountriesFragmentToCountryDetailsFragment(item))
                }
            }
            //Inflate the layout and send it to the adapter
            aAdapter.expressionOnCreateViewHolder = { viewGroup ->
                ItemCountryNestedRecyclerBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )
            }

            binding.aRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = aAdapter
                isNestedScrollingEnabled = false
            }




            //Setup B countries RV
            val bAdapter = BaseRecyclerAdapter<MyCountry>()
            bAdapter.listOfItems = viewModel.myResponse.value?.filter {country -> country.name?.get(0) == 'B' }
            Log.e("LISX", "${bAdapter.listOfItems}")

            bAdapter.expressionViewHolderBinding = { item, viewBinding ->
                val binding = viewBinding as ItemCountryNestedRecyclerBinding

                binding.countryName.text = item.name
                binding.countryCapital.text = item.capital?.get(0)
                binding.countryFlag.load(item.flag)
            }
            //Inflate the layout and send it to the adapter
            bAdapter.expressionOnCreateViewHolder = { viewGroup ->
                ItemCountryNestedRecyclerBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )
            }

            binding.bRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = bAdapter
                isNestedScrollingEnabled = false
            }




            //Setup C countries RV
            val cAdapter = BaseRecyclerAdapter<MyCountry>()
            cAdapter.listOfItems = viewModel.myResponse.value?.filter {country -> country.name?.get(0) == 'B' }
            Log.e("LISX", "${cAdapter.listOfItems}")

            cAdapter.expressionViewHolderBinding = { item, viewBinding ->
                val binding = viewBinding as ItemCountryNestedRecyclerBinding

                binding.countryName.text = item.name
                binding.countryCapital.text = item.capital?.get(0)
                binding.countryFlag.load(item.flag)
            }
            //Inflate the layout and send it to the adapter
            cAdapter.expressionOnCreateViewHolder = { viewGroup ->
                ItemCountryNestedRecyclerBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )
            }

            binding.cRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = cAdapter
                isNestedScrollingEnabled = false
            }







            //Setup D countries RV
            val dAdapter = BaseRecyclerAdapter<MyCountry>()
            dAdapter.listOfItems = viewModel.myResponse.value?.filter {country -> country.name?.get(0) == 'B' }
            Log.e("LISX", "${dAdapter.listOfItems}")

            dAdapter.expressionViewHolderBinding = { item, viewBinding ->
                val binding = viewBinding as ItemCountryNestedRecyclerBinding

                binding.countryName.text = item.name
                binding.countryCapital.text = item.capital?.get(0)
                binding.countryFlag.load(item.flag)
            }
            //Inflate the layout and send it to the adapter
            dAdapter.expressionOnCreateViewHolder = { viewGroup ->
                ItemCountryNestedRecyclerBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )
            }

            binding.cRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = dAdapter
                isNestedScrollingEnabled = false
            }

        }




    }




}









//        binding.languageSelect.setOnClickListener {
//            val languageBottomSheetDialog = BottomSheetDialog(
//                requireContext(),
//                R.style.BottomSheetDialogTheme
//            )
//
////            val bottomSheetView = LayoutInflater.from(requireContext()).inflate(
////                R.layout.language_bottom_sheet,
////            )
//        }


















//            countriesList.addAll(it)
//
//
//            //Setup country info RV
//            val tAdapter = BaseRecyclerAdapter<MyCountry>()
//            tAdapter.listOfItems = viewModel.myResponse.value?.filter {country -> country.name?.get(0) == 'A' }
//            Log.e("LISX", "${tAdapter.listOfItems}")
//
//            tAdapter.expressionViewHolderBinding = { item, viewBinding ->
//                val binding = viewBinding as ItemCountryNestedRecyclerBinding
//
//                binding.countryName.text = item.name
//                binding.countryCapital.text = item.capital?.get(0)
//                binding.countryFlag.load(item.flag)
//            }
//
//            //Inflate the layout and send it to the adapter
//            tAdapter.expressionOnCreateViewHolder = { viewGroup ->
//                ItemCountryNestedRecyclerBinding.inflate(
//                    LayoutInflater.from(viewGroup.context),
//                    viewGroup,
//                    false
//                )
//            }
//
//
//
//
//
//            //Setup alphabets RV
//            val mAdapter = BaseRecyclerAdapter<Char>()
//            mAdapter.listOfItems = Data.alphabets
//            mAdapter.expressionViewHolderBinding = { item, viewBinding ->
//                val binding = viewBinding as ItemCountryAlphabetParentRecyclerBinding
//                binding.alphabet.text = item.toString()
//
//
//                val childMembersAdapter = tAdapter
//
//
//                binding.countryInfoRecyclerView.apply {
//                layoutManager = LinearLayoutManager(context)
//                adapter = tAdapter
//                isNestedScrollingEnabled = false
//                }
//            }
//            //Inflate the layout and send it to the adapter
//            mAdapter.expressionOnCreateViewHolder = { viewGroup ->
//                ItemCountryAlphabetParentRecyclerBinding.inflate(
//                    LayoutInflater.from(viewGroup.context),
//                    viewGroup,
//                    false
//                )
//            }
////            binding.countriesRecyclerView.apply {
////                layoutManager = LinearLayoutManager(context)
////                adapter = mAdapter
////            }