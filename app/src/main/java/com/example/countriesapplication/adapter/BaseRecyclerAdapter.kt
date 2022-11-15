package com.example.countriesapplication.adapter

import android.util.Log
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class BaseViewHolder<T> internal constructor(
    private val binding: ViewBinding,
    private val expression: (T, ViewBinding) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: T) {
        expression(item, binding)
    }
}


class BaseRecyclerAdapter<T> : RecyclerView.Adapter<BaseViewHolder<T>>(), Filterable {

    var listOfItems: List<T>? = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    val listOfItemsFiltered: MutableList<T> = mutableListOf()


    var expressionViewHolderBinding: ((T, ViewBinding) -> Unit)? = null
    var expressionOnCreateViewHolder: ((ViewGroup, Int) -> ViewBinding)? = null
    lateinit var expressionOnGetItemViewType: (item: T?) -> Int
    lateinit var filterCriteria: (item: T, query: String) -> Boolean

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {

        return expressionOnCreateViewHolder?.let { it(parent, viewType) }
            ?.let { BaseViewHolder(it, expressionViewHolderBinding!!) }!!
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(listOfItems!![position])
    }

    override fun getItemCount(): Int {
        return listOfItems?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return expressionOnGetItemViewType.invoke(listOfItems?.get(position))
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val charString = p0.toString() ?: ""
                if (charString.isEmpty()) {
                    listOfItemsFiltered.clear()
                    listOfItemsFiltered.addAll(listOfItems!!)
                } else {
                    val filteredList = ArrayList<T>()
                    listOfItems?.filter {
                        filterCriteria.invoke(it, charString)
                    }?.forEach {
                        filteredList.add(it)
                        listOfItemsFiltered.addAll(filteredList)
                    }
                }
                Log.e("SearchText", charString)
                Log.e("filterResuslt", "$listOfItemsFiltered")
                Log.e("filteList", "$listOfItems.")

                return FilterResults().apply { values = listOfItemsFiltered }
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                if (p1?.values == null){
                    listOfItemsFiltered.clear()
                }else{
                    p1.values as ArrayList<T>
                    notifyDataSetChanged()

                }

//                Log.e("SearchText", charString)
//                Log.e("filterResuslt", "$listOfItemsFiltered")
            }

        }
    }

}