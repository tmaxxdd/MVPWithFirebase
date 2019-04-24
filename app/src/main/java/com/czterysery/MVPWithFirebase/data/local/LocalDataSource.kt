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

    inline fun <reified T> storeTopics(context: Context, ref: String, topics: ArrayList<T>){
        val gsonUtil = GsonUtil(context)
        gsonUtil.storeObjectsArray(ref, topics)
    }

    override fun getContent(context: Context, ref: String, callback: GetContentCallback) {
        val gsonUtil = GsonUtil(context)
        val retrievedContent = gsonUtil.retrieveObjectsArray<Content>(ref)
        callback.onSuccess(retrievedContent)
    }

    fun storeContent(context: Context, ref: String, contents: ArrayList<Content>) {
        val gsonUtil = GsonUtil(context)
        gsonUtil.storeObjectsArray<Content>(ref, contents)
    }

    override fun getContentInfo(context: Context, ref: String, callback: GetContentInfoCallback){
        val gsonUtil = GsonUtil(context)
        val item: ContentInfo = gsonUtil.retrieveObject<ContentInfo>(ref) as ContentInfo
        callback.onSuccess(item)
    }

    fun storeContentInfo(context: Context, ref: String, info: ContentInfo) {
        val gsonUtil = GsonUtil(context)
        gsonUtil.storeObject<ContentInfo>(ref, info)
    }

    fun storeDetails(context: Context, ref: String, details: ArrayList<Detail>) {
        val gsonUtil = GsonUtil(context)
        gsonUtil.storeObjectsArray<Detail>(ref, details)
    }

    override fun getDetails(context: Context, ref: String, callback: GetDetailsCallback) {
        val gsonUtil = GsonUtil(context)
        val details = gsonUtil.retrieveObjectsArray<Detail>(ref)
        callback.onSuccess(details)
    }

}