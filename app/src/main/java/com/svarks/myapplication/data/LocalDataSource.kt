package com.svarks.myapplication.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.svarks.myapplication.data.model.Menus
import com.svarks.myapplication.data.model.Restaurants
import com.svarks.myapplication.utils.AppConstant
import com.svarks.myapplication.utils.CommonUtils

/**
 * Fetching data from local
 */
class LocalDataSource {

    /**
     * @param context
     * @return returns the Restaurants data
     */
    fun getRestaurants(context: Context): Restaurants? {
        val jsonFileString = CommonUtils.getJsonDataFromAsset(context, AppConstant.RESTAURANTS_JSON)
        val gson = Gson()
        val listType = object : TypeToken<Restaurants>() {}.type
        return gson.fromJson(jsonFileString, listType)
    }

    /**
     * @param context
     * @return returns the Menus data
     */
    fun getMenus(context: Context): Menus? {
        val jsonFileString = CommonUtils.getJsonDataFromAsset(context, AppConstant.MENU_JSON)
        val gson = Gson()
        val listType = object : TypeToken<Menus>() {}.type
        return gson.fromJson(jsonFileString, listType)
    }
}
