package com.svarks.myapplication.utils

import android.content.Context
import com.svarks.myapplication.data.model.OperatingHours
import java.io.IOException

object CommonUtils {

    /**
     * @param context
     * @param fileName
     * @return Converts the json into string
     */
    fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    /**
     * @param operatingHours
     * @return it will return the opening hours of the week in a string
     */
    fun getOpeningHours(operatingHours: OperatingHours): String {
        return operatingHours.monday + "(Mon), " +
            operatingHours.tuesday + "(Tue), " +
            operatingHours.wednesday + "(Wed), " +
            operatingHours.thursday + "(Thur), " +
            operatingHours.friday + "(Fri), " +
            operatingHours.saturday + "(Sat), " +
            operatingHours.sunday + "(Sun)"
    }
}
