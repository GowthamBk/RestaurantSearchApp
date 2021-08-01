package com.svarks.myapplication.data.model

import com.google.gson.annotations.SerializedName

data class Menus(
    @SerializedName("menus")
    val menus: List<Menu>
)

data class Menu(
    @SerializedName("categories")
    val categories: List<Category>,
    @SerializedName("restaurantId")
    val restaurantId: Int
)

data class Category(
    @SerializedName("id")
    val id: String,
    @SerializedName("menuItems")
    val menuItems: List<MenuItem>,
    @SerializedName("name")
    val name: String
)

data class MenuItem(
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("images")
    val images: List<Any>,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: String
)
