package com.czterysery.MVPWithFirebase.data

import android.content.Context
import android.util.Log
import com.czterysery.MVPWithFirebase.data.local.LocalDataSource
import com.czterysery.MVPWithFirebase.data.models.Content
import com.czterysery.MVPWithFirebase.data.models.ContentInfo
import com.czterysery.MVPWithFirebase.data.models.Detail
import com.czterysery.MVPWithFirebase.data.models.Topic
import com.czterysery.MVPWithFirebase.util.NetworkHelper

/*
    This class according to a network status returns data from
    the Firebase or a local data repository.
 */
class DataRepository(private val remoteDataSource: DataSource,
                     private val localDataSource: DataSource,
                     private val networkHelper: NetworkHelper) {
    private val TAG = javaClass.simpleName

    fun getTopics(context: Context, ref: String, callback: DataSource.GetTopicsCallback) {
        if (networkHelper.isNetworkAvailable(context)) {
            //Internet connection available
            Log.d(TAG, "Connected to the network.")
            remoteDataSource.getTopics(context, ref, object : DataSource.GetTopicsCallback {

                override fun onSuccess(topics: ArrayList<Topic>) {
                    callback.onSuccess(topics)
                    (localDataSource as LocalDataSource).storeData(context, ref, topics)
                }

                override fun onFailure(throwable: Throwable) {
                    callback.onFailure(throwable)
                }

                override fun onNetworkFailure() {
                    callback.onNetworkFailure()
                }
            })
        } else {
            Log.d(TAG, "Cannot connect to the network")
            localDataSource.getTopics(context, ref, callback)
        }
    }

    fun getContent(context: Context, ref: String, callback: DataSource.GetContentCallback) {
        val issuesRef = "$ref/Issues"

        if (networkHelper.isNetworkAvailable(context)) {
            //Internet connection available
            Log.d(TAG, "Connected to the network.")

            remoteDataSource.getContent(context, ref, object : DataSource.GetContentCallback {

                override fun onSuccess(contents: ArrayList<Content>) {
                    callback.onSuccess(contents)

                    (localDataSource as LocalDataSource).storeData(context, issuesRef, contents)
                }

                override fun onFailure(throwable: Throwable) {
                    callback.onFailure(throwable)
                }

                override fun onNetworkFailure() {
                    callback.onNetworkFailure()
                }

            })
        } else {
            Log.d(TAG, "Cannot connect to the network")
            localDataSource.getContent(context, issuesRef, callback)
        }
    }

    fun getContentInfo(context: Context, ref: String, callback: DataSource.GetContentInfoCallback){

        if (networkHelper.isNetworkAvailable(context)) {
            //Internet connection available
            remoteDataSource.getContentInfo(context, ref, object : DataSource.GetContentInfoCallback {

                override fun onSuccess(info: ContentInfo) {
                    callback.onSuccess(info)
                    (localDataSource as LocalDataSource).storeData(context, ref, info)
                }

                override fun onFailure(throwable: Throwable) {
                    callback.onFailure(throwable)
                }

                override fun onNetworkFailure() {
                    callback.onNetworkFailure()
                }
            })
        } else {
            Log.d(TAG, "Cannot connect to the network")
            localDataSource.getContentInfo(context, ref, callback)
        }
    }

    fun getDetails(context: Context, ref: String, callback: DataSource.GetDetailsCallback) {

        if (networkHelper.isNetworkAvailable(context)) {
            //Internet connection available
            remoteDataSource.getDetails(context, ref, object : DataSource.GetDetailsCallback {

                override fun onSuccess(details: ArrayList<Detail>) {
                    callback.onSuccess(details)
                    (localDataSource as LocalDataSource).storeData(context, ref, details)
                }

                override fun onFailure(throwable: Throwable) {
                    callback.onFailure(throwable)
                }

                override fun onNetworkFailure() {
                    callback.onNetworkFailure()
                }
            })
        } else {
            Log.d(TAG, "Cannot connect to the network")
            localDataSource.getDetails(context, ref, callback)
        }
    }

}