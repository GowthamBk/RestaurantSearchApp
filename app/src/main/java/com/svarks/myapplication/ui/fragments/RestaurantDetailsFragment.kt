package com.svarks.myapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.svarks.myapplication.adapter.ReviewAdapter
import com.svarks.myapplication.data.model.Restaurant
import com.svarks.myapplication.data.model.Review
import com.svarks.myapplication.databinding.FragmentRestaurantDetailsBinding
import com.svarks.myapplication.utils.AppConstant
import com.svarks.myapplication.utils.CommonUtils

class RestaurantDetailsFragment : Fragment() {

    private lateinit var binding: FragmentRestaurantDetailsBinding
    private lateinit var reviewAdapter: ReviewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRestaurantDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initUI()
    }

    private fun initUI() {
        val restaurant: Restaurant? = arguments?.getParcelable(AppConstant.RESTAURANTS_DATA)
        restaurant.let { restaurantData ->
            binding.apply {
                tvRestaurantName.text = restaurantData?.name
                tvRestaurantNeighborhood.text = restaurantData?.neighborhood
                tvRestaurantAddress.text = restaurantData?.address
                tvRestaurantCuisineType.text = restaurantData?.cuisineType
                tvOpenTime.text = CommonUtils.getOpeningHours(restaurantData?.operatingHours!!)
                initAdapter(restaurantData.reviews)
            }
        }
    }

    private fun initAdapter(reviews: List<Review>) {
        reviewAdapter = ReviewAdapter(reviews)
        binding.rvReviews.apply {
            adapter = reviewAdapter
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
        }
    }
}
