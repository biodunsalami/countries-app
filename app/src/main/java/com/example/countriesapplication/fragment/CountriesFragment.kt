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

    private var aAdapter: BaseRecyclerAdapter<CountryListItem> = BaseRecyclerAdapter()


    var tempList = ArrayList<CountryListItem>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCountriesBinding.inflate(inflater, container, false)



        aAdapter.filterCriteria = { item, query ->
            when (item) {
                is MyCountry -> {
                    item.name?.contains(query)!!
                }
                is CountryHeader -> {
                    false
                }
            }
        }

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
            if (binding.uiModeCheckbox.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }

            if (!binding.uiModeCheckbox.isChecked) {
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

//            performSearch(it)
        }

        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                aAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                aAdapter.filter.filter(newText)

                aAdapter.filterCriteria = { item, query ->
                    when (item) {
                        is MyCountry -> {
                            item.name?.contains(query)!!
                        }
                        is CountryHeader -> {
                            false
                        }
                    }
                }
                return false
            }

        })



    }

    private fun performSearch(list: List<CountryListItem>) {




        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                binding.searchTextView.visibility = View.INVISIBLE

                tempList.clear()
                val searchText = newText!!.lowercase(Locale.getDefault())

                if (searchText.isNotEmpty()) {

                    for (item in list) {
                        when (item) {
                            is MyCountry -> {

//                                list.filter { item.name?.contains(searchText) == true }

                                if (item.name?.contains(searchText) == true) {
                                    tempList.add(item)
//                                    Log.e("TheText",  )
                                }
                            }
                            is CountryHeader -> {}
                        }

                    }

                } else {
                    tempList.clear()
                    tempList.addAll(list)
                    setUpCountriesRv(tempList)
                    aAdapter.notifyDataSetChanged()
                    binding.searchTextView.visibility = View.VISIBLE

                }



                return false
            }
        })


    }

    private fun setUpFilterCategoriesRv() {

        val nestedAdapter = BaseRecyclerAdapter<FilterModel.Member>()

//        val list = arrayListOf<FilterModel.Member>(Data.timeZones )

        nestedAdapter.expressionOnGetItemViewType = { _ -> 0 }

        nestedAdapter.expressionViewHolderBinding = { item, viewBinding ->
            val binding = viewBinding as ItemFilterNestedRecyclerBinding

            binding.filterItemText.text = item.title


        }

        nestedAdapter.expressionOnCreateViewHolder = { viewGroup, viewType ->
            ItemFilterNestedRecyclerBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        }


        val filterAdapter = BaseRecyclerAdapter<FilterModel>()

        filterAdapter.listOfItems = Data.allModels

        filterAdapter.expressionOnGetItemViewType = { _ -> 0 }

        filterAdapter.expressionViewHolderBinding = { item, viewBinding ->
            val binding = viewBinding as ItemFilterParentRecyclerBinding

            binding.filterItemText.text = item.filterCategoryTitle

            binding.nestedRv.apply {
                nestedAdapter.listOfItems = Data.continent

                layoutManager = LinearLayoutManager(context)
                adapter = nestedAdapter
                isNestedScrollingEnabled = false
            }
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

    }


    private fun setUpCountriesRv(list: List<CountryListItem>) {
        //Setup countries RV
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

    private fun switchUIMode() {

    }

}
