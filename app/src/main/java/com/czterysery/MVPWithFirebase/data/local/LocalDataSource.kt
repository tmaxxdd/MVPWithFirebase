package com.czterysery.MVPWithFirebase.data.local

import android.content.Context
import com.czterysery.MVPWithFirebase.data.DataSource
import com.czterysery.MVPWithFirebase.data.models.Content
import com.czterysery.MVPWithFirebase.data.models.ContentInfo
import com.czterysery.MVPWithFirebase.data.models.Detail
import com.czterysery.MVPWithFirebase.data.models.Topic
import com.czterysery.MVPWithFirebase.util.GsonUtil

/**
 * Created by tmax0 on 24.12.2017.
 */
class LocalDataSource : DataSource() {
    private val TAG = javaClass.simpleName

    override fun getTopics(context: Context, ref: String, callback: GetTopicsCallback) {
        val gsonUtil = GsonUtil(context)
        val retrievedTopics = gsonUtil.retrieveObjectsArray<Topic>(ref)
        callback.onSuccess(retrievedTopics)
    }

    override fun getContent(context: Context, ref: String, callback: GetContentCallback) {
        val gsonUtil = GsonUtil(context)
        val retrievedContent = gsonUtil.retrieveObjectsArray<Content>(ref)
        callback.onSuccess(retrievedContent)
    }

    override fun getContentInfo(context: Context, ref: String, callback: GetContentInfoCallback){
        val gsonUtil = GsonUtil(context)
        val item: ContentInfo = gsonUtil.retrieveObject<ContentInfo>(ref) as ContentInfo
        callback.onSuccess(item)
    }

    override fun getDetails(context: Context, ref: String, callback: GetDetailsCallback) {
        val gsonUtil = GsonUtil(context)
        val details = gsonUtil.retrieveObjectsArray<Detail>(ref)
        callback.onSuccess(details)
    }

    inline fun <reified T> storeData(context: Context, ref: String, data: ArrayList<T>) {
        val gsonUtil = GsonUtil(context)
        gsonUtil.storeObjectsArray(ref, data)
    }

    inline fun <reified T> storeData(context: Context, ref: String, data: T) {
        val gsonUtil = GsonUtil(context)
        gsonUtil.storeObject<T>(ref, data as Any)
    }

}