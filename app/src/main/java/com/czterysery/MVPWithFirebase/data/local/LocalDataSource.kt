package com.czterysery.MVPWithFirebase.data.local

import com.czterysery.MVPWithFirebase.data.DataSource
import com.czterysery.MVPWithFirebase.data.models.ContentInfo
import com.czterysery.MVPWithFirebase.util.helpers.GsonHelper


/*
    This class is responsible for storing and retrieving data from
    local source (GSON). More about GSON you can learn here:
    https://github.com/google/gson
 */
class LocalDataSource(val gsonHelper: GsonHelper) : DataSource() {
    private val TAG = javaClass.simpleName

    override fun getTopics(ref: String, callback: GetTopicsCallback) {
        callback.onSuccess(retrieveData(ref))
    }

    override fun getContent(ref: String, callback: GetContentCallback) {
        callback.onSuccess(retrieveData(ref))
    }

    override fun getContentInfo(ref: String, callback: GetContentInfoCallback){
        val item: ContentInfo = gsonHelper.retrieveObject<ContentInfo>(ref) as ContentInfo
        callback.onSuccess(item)
    }

    override fun getDetails(ref: String, callback: GetDetailsCallback) {
        callback.onSuccess(retrieveData(ref))
    }

    inline fun <reified T> storeData(ref: String, data: ArrayList<T>) {
        gsonHelper.storeObjectsArray(ref, data)
    }

    inline fun <reified T> storeData(ref: String, data: T) {
        gsonHelper.storeObject<T>(ref, data as Any)
    }

    private inline fun <reified T> retrieveData(ref: String): java.util.ArrayList<T> {
        return gsonHelper.retrieveObjectsArray(ref)
    }

}