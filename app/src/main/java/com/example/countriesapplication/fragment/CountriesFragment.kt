package com.example.countriesapplication.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.example.countriesapplication.*
import com.example.countriesapplication.adapter.BaseRecyclerAdapter
import com.example.countriesapplication.databinding.*
import com.example.countriesapplication.viewModels.CountriesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.lang.IllegalArgumentException
import java.util.*
import kotlin.collections.ArrayList


class CountriesFragment : Fragment() {

    private lateinit var binding: FragmentCountriesBinding

    private val repository = Repository(Api.apiService)

    lateinit var viewModel: CountriesViewModel


    lateinit var languagesBottomSheetBinding: BottomSheetLanguageBinding
    lateinit var languagesBottomSheetDialog: BottomSheetDialog


    lateinit var filterBottomSheetBinding: BottomSheetFilterBinding
    lateinit var filterBottomSheetDialog: BottomSheetDialog


    var tempList = ArrayList<CountryListItem>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCountriesBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            ViewModelProvider(this, MyViewModelFactory(repository))[CountriesViewModel::class.java]


        languagesBottomSheetBinding =
            BottomSheetLanguageBinding.inflate(layoutInflater, null, false)
        languagesBottomSheetDialog()

        filterBottomSheetBinding = BottomSheetFilterBinding.inflate(layoutInflater, null, false)
        filterBottomSheetDialog()

        viewModel.getCountry()
        setLanguage()



        setUpFilterCategoriesRv()


        binding.uiModeCheckbox.setOnClickListener {
            if (binding.uiModeCheckbox.isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }

            if (!binding.uiModeCheckbox.isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

        }


        binding.languageSelect.setOnClickListener {
            languagesBottomSheetDialog.show()
        }




        languagesBottomSheetBinding.closeSheet.setOnClickListener {
            languagesBottomSheetDialog.cancel()
        }

        binding.filter.setOnClickListener {
            filterBottomSheetDialog.show()
        }

        filterBottomSheetBinding.closeSheet.setOnClickListener {
            filterBottomSheetDialog.cancel()
        }


        viewModel.myResponse.observe(viewLifecycleOwner) {
            if (it == null) {
                return@observe
            }

            tempList.addAll(it)

            setUpCountriesRv(tempList)


            binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    binding.searchTextView.visibility = View.VISIBLE

                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {

                    binding.searchTextView.visibility = View.INVISIBLE

                    tempList.clear()
                    val searchText = newText!!.lowercase(Locale.getDefault())



                    for (item in it) {
                        when (item) {
                            is MyCountry -> {

                                if (item.name?.contains(searchText) == true) {
                                    tempList.add(item)
                                }
                            }
                            is CountryHeader -> {}
                        }

                    }
                    return false
                }
            })

            Log.e("FILTER_LIST", "$it")


        }

    }

    private fun performSearch(list: List<CountryListItem>) {

        Log.e("call", "E call am o")


    }


    private fun setUpCountriesRv(list: List<CountryListItem>) {
        //Setup countries RV
        val aAdapter = BaseRecyclerAdapter<CountryListItem>()
        aAdapter.listOfItems = list

        aAdapter.expressionOnGetItemViewType = { country ->
            when (country) {
                is CountryHeader -> 1
                is MyCountry -> 0
                null -> throw IllegalArgumentException("fatal error")
            }
            //if (country?.name?.length == 1) 1 else 0
        }

        aAdapter.expressionViewHolderBinding = { item, viewBinding ->
            when (item) {
                is CountryHeader -> {
                    viewBinding as ItemAlphabetRecyclerBinding
                    viewBinding.alphabet.text = item.header.toString()
                }
                is MyCountry -> {
                    viewBinding as ItemCountryRecyclerBinding

                    viewBinding.countryName.text = item.name
                    viewBinding.countryCapital.text = item.capital?.get(0)
                    viewBinding.countryFlag.load(item.flag?.png)


                    viewBinding.countryInfo.setOnClickListener {
                        findNavController().navigate(
                            CountriesFragmentDirections.actionCountriesFragmentToCountryDetailsFragment(
                                item
                            )
                        )
                    }
                }
            }
        }
        //Inflate the layout and send it to the adapter
        aAdapter.expressionOnCreateViewHolder = { viewGroup, viewType ->
            val inflater = LayoutInflater.from(viewGroup.context)
            if (viewType == 0) {
                ItemCountryRecyclerBinding.inflate(
                    inflater,
                    viewGroup,
                    false
                )
            } else {
                ItemAlphabetRecyclerBinding.inflate(
                    inflater,
                    viewGroup,
                    false
                )
            }
        }

        binding.countriesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = aAdapter
        }

        aAdapter.notifyDataSetChanged()
    }

    private fun setUpFilterCategoriesRv() {
        val filterAdapter = BaseRecyclerAdapter<FilterModel>()


        filterAdapter.listOfItems = Data.nestedList

        filterAdapter.expressionOnGetItemViewType = { _ -> 0 }

        filterAdapter.expressionViewHolderBinding = { item, viewBinding ->
            val binding = viewBinding as ItemFilterParentRecyclerBinding

            binding.filterItemText.text = item.filterCategoryTitle
        }

        filterAdapter.expressionOnCreateViewHolder = { viewGroup, viewType ->
            ItemFilterParentRecyclerBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        }

        filterBottomSheetBinding.filterRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = filterAdapter
            isNestedScrollingEnabled = false

        }

        filterBottomSheetBinding.filterRecyclerView


        val nestedAdapter = BaseRecyclerAdapter<FilterModel>()


        nestedAdapter.listOfItems = Data.nestedList

        nestedAdapter.expressionOnGetItemViewType = { _ -> 0 }

        nestedAdapter.expressionViewHolderBinding = { item, viewBinding ->
            val binding = viewBinding as ItemFilterNestedRecyclerBinding

            binding.filterItemText.text = item.nestedList[0]

//            binding.


        }

        nestedAdapter.expressionOnCreateViewHolder = { viewGroup, viewType ->
            ItemFilterNestedRecyclerBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        }

//        filterBottomSheetBinding.filterRecyclerView.apply {
//            layoutManager = LinearLayoutManager(context)
//            adapter = filterAdapter
//            isNestedScrollingEnabled = false
//
//        }


    }


    private fun languagesBottomSheetDialog() {
        languagesBottomSheetDialog = BottomSheetDialog(
            requireContext(),
            R.style.BottomSheetDialogTheme
        )

        languagesBottomSheetDialog.setContentView(languagesBottomSheetBinding.root)
    }

    private fun filterBottomSheetDialog() {
        filterBottomSheetDialog = BottomSheetDialog(
            requireContext(),
            R.style.BottomSheetDialogTheme
        )

        filterBottomSheetDialog.setContentView(filterBottomSheetBinding.root)


    }

    private fun setLanguage() {

        binding.languageShort.text = getString(R.string.en)

        languagesBottomSheetBinding.languageRadioGroup.setOnCheckedChangeListener { radioGroup, i ->
            if (i == R.id.bahasa) {
                binding.languageShort.text = getString(R.string.bh)
            }
            if (i == R.id.deutsch) {
                binding.languageShort.text = getString(R.string.du)
            }
            if (i == R.id.english) {
                binding.languageShort.text = getString(R.string.en)
            }
            if (i == R.id.espanol) {
                binding.languageShort.text = getString(R.string.es)
            }
            if (i == R.id.french) {
                binding.languageShort.text = getString(R.string.fr)
            }
            if (i == R.id.italiano) {
                binding.languageShort.text = getString(R.string.it)
            }
            if (i == R.id.portugues) {
                binding.languageShort.text = getString(R.string.pg)
            }
            if (i == R.id.russian) {
                binding.languageShort.text = getString(R.string.ru)
            }
            if (i == R.id.swedish) {
                binding.languageShort.text = getString(R.string.sw)
            }
            if (i == R.id.turkish) {
                binding.languageShort.text = getString(R.string.tr)
            }
            if (i == R.id.chinese) {
                binding.languageShort.text = getString(R.string.ch)
            }
            if (i == R.id.arabic) {
                binding.languageShort.text = getString(R.string.ar)
            }
            if (i == R.id.bengali) {
                binding.languageShort.text = getString(R.string.be)
            }
        }

    }

    private fun switchUIMode(){

    }

}
