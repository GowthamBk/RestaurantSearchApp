package com.svarks.myapplication.ui.fragments

import android.app.SearchManager
import android.database.Cursor
import android.database.MatrixCursor
import android.os.Bundle
import android.provider.BaseColumns
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import androidx.appcompat.widget.SearchView
import androidx.cursoradapter.widget.CursorAdapter
import androidx.cursoradapter.widget.SimpleCursorAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.svarks.myapplication.R
import com.svarks.myapplication.adapter.RestaurantAdapter
import com.svarks.myapplication.data.LocalDataSource
import com.svarks.myapplication.data.Repository
import com.svarks.myapplication.data.model.Restaurant
import com.svarks.myapplication.databinding.FragmentRestaurantBinding
import com.svarks.myapplication.utils.AppConstant
import com.svarks.myapplication.utils.RestaurantViewModelFactory
import com.svarks.myapplication.utils.hideKeyboard
import com.svarks.myapplication.viewmodels.RestaurantViewModel

class RestaurantFragment :
    Fragment() {

    private lateinit var restaurantAdapter: RestaurantAdapter
    private lateinit var restaurantViewModel: RestaurantViewModel
    private lateinit var binding: FragmentRestaurantBinding
    private lateinit var cursorAdapter: CursorAdapter
    private var suggestionName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRestaurantBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        setupViewModel()
        initUI()
    }

    private fun setupViewModel() {
        restaurantViewModel = ViewModelProvider(
            this,
            RestaurantViewModelFactory(
                Repository(
                    LocalDataSource()
                ),
                requireActivity()
            )
        ).get(RestaurantViewModel::class.java)
    }

    private fun initUI() {
        restaurantViewModel.getData()
        if (suggestionName.isNullOrBlank()) {
            initAdapter(restaurantViewModel.restaurants.value?.restaurants)
        } else {
            initAdapter(restaurantViewModel.getRestaurantList(suggestionName!!))
        }
        setUpSearchView()
        restaurantSuggestionListener()
        restaurantQueryListener()
    }

    private fun setUpSearchView() {
        cursorAdapter = SimpleCursorAdapter(
            context,
            R.layout.search_item,
            null,
            arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1),
            intArrayOf(R.id.item_label),
            CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        )
        binding.searchRestaurant.also { searchView ->
            searchView.queryHint = getString(R.string.search)
            searchView.findViewById<AutoCompleteTextView>(R.id.search_src_text).threshold = 1
            searchView.suggestionsAdapter = cursorAdapter
        }
    }

    private fun restaurantSuggestionListener() {
        binding.searchRestaurant.setOnSuggestionListener(object :
                SearchView.OnSuggestionListener,
                android.widget.SearchView.OnSuggestionListener {
                override fun onSuggestionSelect(position: Int): Boolean {
                    return false
                }

                override fun onSuggestionClick(position: Int): Boolean {
                    hideKeyboard()
                    val cursor = binding.searchRestaurant.suggestionsAdapter.getItem(position) as Cursor
                    val selection =
                        cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1))
                    binding.searchRestaurant.setQuery(selection, false)
                    val restaurantList = restaurantViewModel.getRestaurantList(selection)
                    if (restaurantList.isNullOrEmpty()) {
                        // Restaurant not found
                        binding.tvNoRestaurantFound.visibility = VISIBLE
                        binding.imgNoRestaurantFound.visibility = VISIBLE
                        binding.rvRestaurantList.visibility = GONE
                    } else {
                        binding.tvNoRestaurantFound.visibility = GONE
                        binding.imgNoRestaurantFound.visibility = GONE
                        binding.rvRestaurantList.visibility = VISIBLE
                        initAdapter(restaurantList)
                    }
                    return true
                }
            })
    }

    private fun restaurantQueryListener() {
        binding.searchRestaurant.setOnQueryTextListener(object :
                SearchView.OnQueryTextListener,
                android.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    hideKeyboard()
                    return false
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    val cursor =
                        MatrixCursor(arrayOf(BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1))
                    query?.let { queryStr ->
                        suggestionName = queryStr
                        if (queryStr.isBlank()) {
                            initAdapter(restaurantViewModel.restaurants.value?.restaurants)
                        } else {
                            restaurantViewModel.getSuggestions()
                                .forEachIndexed { index, suggestion ->
                                    if (suggestion.contains(queryStr, true))
                                        cursor.addRow(arrayOf(index, suggestion))
                                }
                        }
                    }
                    cursorAdapter.changeCursor(cursor)
                    return true
                }
            })
    }

    private fun initAdapter(restaurants: List<Restaurant>?) {
        restaurantAdapter = RestaurantAdapter(
            restaurants!!,
            object : RestaurantAdapter.OnItemClickListener {
                override fun onItemClick(restaurant: Restaurant) {
                    val bundle = Bundle().apply {
                        putParcelable(AppConstant.RESTAURANTS_DATA, restaurant)
                    }
                    findNavController().navigate(
                        R.id.action_restaurantFragment_to_restaurantDetailsFragment,
                        bundle
                    )
                }
            }
        )
        binding.rvRestaurantList.apply {
            adapter = restaurantAdapter
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
        }
    }
}
