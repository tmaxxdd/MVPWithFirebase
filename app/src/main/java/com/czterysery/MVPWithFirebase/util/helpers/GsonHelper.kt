package com.czterysery.MVPWithFirebase.util.helpers

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

/*
    This class takes kotlin objects like arrays or classes
    and by Gson library parse them to JSON format.
    SharedPrefsHelper in param is used for storing data on a device.
 */
class GsonHelper(val prefs: SharedPrefsHelper) {
    private val TAG = javaClass.simpleName
    val gson = Gson()

    inline fun <reified T> storeObjectsArray(name: String, list: ArrayList<T>) {
        val typeOfList =
                TypeToken.getParameterized(ArrayList::class.java, T::class.java).type
        val json = gson.toJson(list, typeOfList)
        prefs.write(name, json)
    }

    inline fun <reified T> retrieveObjectsArray(name: String): ArrayList<T>{
        val json = prefs.read(name, "")
        val typeOfList =
                TypeToken.getParameterized(ArrayList::class.java, T::class.java).type
        return gson.fromJson(json, typeOfList)
    }

    inline fun <reified T> storeObject(name: String, item: Any) {
        val json = gson.toJson(item, T::class.java)
        prefs.write(name, json)
    }

    inline fun <reified T> retrieveObject(name: String): Any {
        val json = prefs.read(name, "")
        return gson.fromJson(json, T::class.java)!!
    }

}