package com.czterysery.MVPWithFirebase.data

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

    fun getTopics(ref: String, callback: DataSource.GetTopicsCallback) {
        if (networkHelper.isNetworkAvailable()) {
            //Internet connection available
            Log.d(TAG, "Connected to the network.")
            remoteDataSource.getTopics(ref, object : DataSource.GetTopicsCallback {

                override fun onSuccess(topics: ArrayList<Topic>) {
                    callback.onSuccess(topics)
                    (localDataSource as LocalDataSource).storeData(ref, topics)
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
            localDataSource.getTopics(ref, callback)
        }
    }

    fun getContent(ref: String, callback: DataSource.GetContentCallback) {
        val issuesRef = "$ref/Issues"

        if (networkHelper.isNetworkAvailable()) {
            //Internet connection available
            Log.d(TAG, "Connected to the network.")

            remoteDataSource.getContent( ref, object : DataSource.GetContentCallback {

                override fun onSuccess(contents: ArrayList<Content>) {
                    callback.onSuccess(contents)

                    (localDataSource as LocalDataSource).storeData(issuesRef, contents)
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
            localDataSource.getContent(issuesRef, callback)
        }
    }

    fun getContentInfo(ref: String, callback: DataSource.GetContentInfoCallback){

        if (networkHelper.isNetworkAvailable()) {
            //Internet connection available
            remoteDataSource.getContentInfo(ref, object : DataSource.GetContentInfoCallback {

                override fun onSuccess(info: ContentInfo) {
                    callback.onSuccess(info)
                    (localDataSource as LocalDataSource).storeData(ref, info)
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
            localDataSource.getContentInfo(ref, callback)
        }
    }

    fun getDetails(ref: String, callback: DataSource.GetDetailsCallback) {

        if (networkHelper.isNetworkAvailable()) {
            //Internet connection available
            remoteDataSource.getDetails(ref, object : DataSource.GetDetailsCallback {

                override fun onSuccess(details: ArrayList<Detail>) {
                    callback.onSuccess(details)
                    (localDataSource as LocalDataSource).storeData(ref, details)
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
            localDataSource.getDetails(ref, callback)
        }
    }

}