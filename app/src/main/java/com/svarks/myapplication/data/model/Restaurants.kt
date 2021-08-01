package com.svarks.myapplication.data.model
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Restaurants(
    @SerializedName("restaurants")
    val restaurants: List<Restaurant>
)

data class Restaurant(
    @SerializedName("address")
    val address: String?,
    @SerializedName("cuisine_type")
    val cuisineType: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("latlng")
    val latlng: Latlng,
    @SerializedName("name")
    val name: String?,
    @SerializedName("neighborhood")
    val neighborhood: String?,
    @SerializedName("operating_hours")
    val operatingHours: OperatingHours,
    @SerializedName("photograph")
    val photograph: String?,
    @SerializedName("reviews")
    val reviews: List<Review>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        TODO("latlng"),
        parcel.readString(),
        parcel.readString(),
        TODO("operatingHours"),
        parcel.readString(),
        TODO("reviews")
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(address)
        parcel.writeString(cuisineType)
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(neighborhood)
        parcel.writeString(photograph)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Restaurant> {
        override fun createFromParcel(parcel: Parcel): Restaurant {
            return Restaurant(parcel)
        }

        override fun newArray(size: Int): Array<Restaurant?> {
            return arrayOfNulls(size)
        }
    }
}

data class Latlng(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double
)

data class OperatingHours(
    @SerializedName("Friday")
    val friday: String,
    @SerializedName("Monday")
    val monday: String,
    @SerializedName("Saturday")
    val saturday: String,
    @SerializedName("Sunday")
    val sunday: String,
    @SerializedName("Thursday")
    val thursday: String,
    @SerializedName("Tuesday")
    val tuesday: String,
    @SerializedName("Wednesday")
    val wednesday: String
)

data class Review(
    @SerializedName("comments")
    val comments: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("rating")
    val rating: Int
)
