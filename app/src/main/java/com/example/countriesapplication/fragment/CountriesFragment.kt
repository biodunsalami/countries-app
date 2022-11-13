package com.example.countriesapplication.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.countriesapplication.*
import com.example.countriesapplication.adapter.BaseRecyclerAdapter
import com.example.countriesapplication.databinding.*
import com.example.countriesapplication.models.Languages
import com.example.countriesapplication.viewModels.CountriesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException


class CountriesFragment : Fragment() {

    private lateinit var binding: FragmentCountriesBinding

    private val repository = Repository(Api.apiService)

    lateinit var viewModel: CountriesViewModel

    lateinit var languagesBottomSheetBinding: BottomSheetLanguageBinding
    lateinit var languagesBottomSheetDialog: BottomSheetDialog



    lateinit var filterBottomSheetBinding: BottomSheetFilterBinding
    lateinit var filterBottomSheetDialog: BottomSheetDialog




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


        languagesBottomSheetBinding = BottomSheetLanguageBinding.inflate(layoutInflater, null, false)
        languagesBottomSheetDialog()

        filterBottomSheetBinding = BottomSheetFilterBinding.inflate(layoutInflater, null, false)
        filterBottomSheetDialog()

        viewModel.getCountry()
        setLanguage()



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

            //Setup countries RV
            val aAdapter = BaseRecyclerAdapter<CountryListItem>()
            aAdapter.listOfItems = it

            aAdapter.expressionOnGetItemViewType = { country ->
                when(country){
                    is CountryHeader -> 1
                    is MyCountry -> 0
                    null -> throw IllegalArgumentException("fatal error")
                }
                //if (country?.name?.length == 1) 1 else 0
            }

            aAdapter.expressionViewHolderBinding = { item, viewBinding ->
                when(item){
                    is CountryHeader -> {
                        viewBinding as ItemAlphabetRecyclerBinding
                        viewBinding.alphabet.text = item.header.toString()
                    }
                    is MyCountry -> {
                        viewBinding as ItemCountryRecyclerBinding

                        viewBinding.countryName.text = item.name
                        viewBinding.countryCapital.text = item.capital?.get(0)
                        viewBinding.countryFlag.load(item.flag)


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
                isNestedScrollingEnabled = false
            }


        }


    }


    private fun languagesBottomSheetDialog() {
        languagesBottomSheetDialog = BottomSheetDialog(
            requireContext(),
            R.style.BottomSheetDialogTheme
        )

        languagesBottomSheetDialog.setContentView(languagesBottomSheetBinding.root)
    }

    private fun filterBottomSheetDialog(){
        filterBottomSheetDialog = BottomSheetDialog(
            requireContext(),
            R.style.BottomSheetDialogTheme
        )

        filterBottomSheetDialog.setContentView(filterBottomSheetBinding.root)


    }

    private fun setLanguage(){
        languagesBottomSheetBinding.bahasa.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) binding.languageShort.text = getString(R.string.bh)
        }


        languagesBottomSheetBinding.deutsch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) binding.languageShort.text = getString(R.string.du)
        }

        languagesBottomSheetBinding.english.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) binding.languageShort.text = getString(R.string.en)
        }

        languagesBottomSheetBinding.espanol.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) binding.languageShort.text = getString(R.string.es)
        }


        languagesBottomSheetBinding.french.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) binding.languageShort.text = getString(R.string.fr)
        }

        languagesBottomSheetBinding.italiano.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) binding.languageShort.text = getString(R.string.it)
        }


        languagesBottomSheetBinding.portugues.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) binding.languageShort.text = getString(R.string.pg)
        }


        languagesBottomSheetBinding.russian.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) binding.languageShort.text = getString(R.string.ru)
        }

        languagesBottomSheetBinding.swedish.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) binding.languageShort.text = getString(R.string.sw)
        }

        languagesBottomSheetBinding.turkish.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) binding.languageShort.text = getString(R.string.tr)
        }


        languagesBottomSheetBinding.chinese.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) binding.languageShort.text = getString(R.string.ch)
        }

        languagesBottomSheetBinding.arabic.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) binding.languageShort.text = getString(R.string.ar)
        }

        languagesBottomSheetBinding.bengali.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) binding.languageShort.text = getString(R.string.be)
        }

    }

}
