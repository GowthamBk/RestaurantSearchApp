package com.svarks.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.svarks.myapplication.data.model.Restaurant
import com.svarks.myapplication.data.model.Restaurants
import com.svarks.myapplication.databinding.ItemRestaurantBinding

/**
 * @param restaurants
 * @param listener
 * Display list of the Restaurants and on click of the particular
 * Restaurant it will re-direct to RestaurantDetailsFragment
 */
class RestaurantAdapter(
    private val restaurants: List<Restaurant>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<RestaurantAdapter.RestaurantHolder>() {

    inner class RestaurantHolder(private val binding: ItemRestaurantBinding) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        init {
            binding.cvListMain.setOnClickListener(this)
        }
        lateinit var data: Restaurant
        fun bind(restaurant: Restaurant) {
            data = restaurant
            binding.apply {
                tvRestaurantName.text = restaurant.name
                tvRestaurantNeighborhood.text = restaurant.neighborhood
                tvRestaurantCuisineType.text = restaurant.cuisineType
            }
        }

        override fun onClick(v: View?) {
            listener.onItemClick(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantHolder {
        val binding = ItemRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RestaurantHolder(binding)
    }

    override fun onBindViewHolder(holder: RestaurantHolder, position: Int) {
        holder.bind(restaurants[position])
    }

    override fun getItemCount(): Int {
        return if (restaurants.isNotEmpty()) restaurants.size else 0
    }

    interface OnItemClickListener {
        fun onItemClick(restaurant: Restaurant)
    }
}
