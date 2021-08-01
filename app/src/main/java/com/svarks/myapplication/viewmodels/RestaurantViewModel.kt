package com.svarks.myapplication.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.svarks.myapplication.data.Repository
import com.svarks.myapplication.data.model.Menus
import com.svarks.myapplication.data.model.Restaurant
import com.svarks.myapplication.data.model.Restaurants

class RestaurantViewModel(
    private val repository: Repository,
    private val context: Context
) : ViewModel() {

    val restaurants: MutableLiveData<Restaurants> = MutableLiveData<Restaurants>()
    private val menus: MutableLiveData<Menus> = MutableLiveData<Menus>()

    /**
     * To fetch Restaurants data
     */
    private fun getRestaurants() {
        restaurants.value = repository.local.getRestaurants(context)
    }

    /**
     * To fetch Menus data
     */
    private fun getMenus() {
        menus.value = repository.local.getMenus(context)
    }

    /**
     * To fetch both Restaurants & Menus data
     */
    fun getData() {
        getRestaurants()
        getMenus()
    }

    /**
     * @return To fetch list of suggestion to display in the search view
     */
    fun getSuggestions(): List<String> {
        val suggestionList: MutableList<String> = ArrayList()
        suggestionList.addAll(restaurantsSuggestionList())
        suggestionList.addAll(menuSuggestionList())
        return suggestionList.distinct()
    }

    /**
     * @return To fetch list of suggestion to display in the search view
     * from restaurant JSON
     */
    private fun restaurantsSuggestionList(): List<String> {
        val suggestionList: MutableList<String> = ArrayList()
        restaurants.value?.restaurants?.map { restaurant ->
            restaurant.name?.let { restaurantName -> suggestionList.add(restaurantName) }
            restaurant.cuisineType?.split(",".toRegex())?.map { cuisine ->
                suggestionList.add(cuisine.trim())
            }
        }
        return suggestionList
    }

    /**
     * @return To fetch list of suggestion to display in the search view
     * from menu JSON
     */
    private fun menuSuggestionList(): List<String> {
        val suggestionList: MutableList<String> = ArrayList()
        menus.value?.menus?.map { menu ->
            menu.categories.map { category ->
                category.menuItems.forEach { menuItem ->
                    suggestionList.add(menuItem.name)
                }
            }
        }
        return suggestionList
    }

    /**
     * @return To fetch list of Restaurant list
     */
    fun getRestaurantList(key: String): List<Restaurant> {
        val restaurantList: MutableList<Restaurant> = ArrayList()
        restaurants.value?.restaurants?.map { restaurant ->
            if (restaurant.name?.contains(key)!! || restaurant.cuisineType?.contains(key)!!) {
                restaurantList.add(restaurant)
            }
            if (menus.value?.menus?.isNotEmpty()!!) {
                getMenuId(key).map { restaurantId ->
                    if (restaurantId == restaurant.id) {
                        restaurantList.add(restaurant)
                    }
                }
            }
        }
        return restaurantList.distinct()
    }

    private fun getMenuId(key: String): List<Int> {
        val integerList: MutableList<Int> = ArrayList()
        menus.value?.menus?.map { menu ->
            menu.categories.map { category ->
                category.menuItems.map { menuItem ->
                    if (menuItem.name.contains(key)) {
                        integerList.add(menu.restaurantId)
                    }
                }
            }
        }

        /*for (i in menus?.menus?.indices!!) {
            categoryLoop@for (j in menus.menus[i].categories.indices) {
                for (k in menus.menus[i].categories[j].menuItems.indices) {
                    if (menus.menus[i].categories[j].menuItems[k].name.contains(key)) {
                        integerList.add(menus.menus[i].restaurantId)
                        break@categoryLoop
                    }
                }
            }
        }*/
        return integerList
    }
}
