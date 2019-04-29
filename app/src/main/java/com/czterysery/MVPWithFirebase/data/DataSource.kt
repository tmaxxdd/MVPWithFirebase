package com.czterysery.MVPWithFirebase.data

import com.czterysery.MVPWithFirebase.data.models.Content
import com.czterysery.MVPWithFirebase.data.models.ContentInfo
import com.czterysery.MVPWithFirebase.data.models.Detail
import com.czterysery.MVPWithFirebase.data.models.Topic

/*
    DataSource is a template for any class that
    provides data.
 */
abstract class DataSource {

    interface GetTopicsCallback {

        fun onSuccess(topics: ArrayList<Topic>)

        fun onFailure(throwable: Throwable)

        fun onNetworkFailure()
    }

    abstract fun getTopics(ref: String, callback: GetTopicsCallback)

    interface GetContentCallback {

        fun onSuccess(contents: ArrayList<Content>)

        fun onFailure(throwable: Throwable)

        fun onNetworkFailure()

    }

    abstract fun getContent(ref: String, callback: GetContentCallback)

    interface GetContentInfoCallback {

        fun onSuccess(info: ContentInfo)

        fun onFailure(throwable: Throwable)

        fun onNetworkFailure()

    }

    abstract fun getContentInfo(ref: String, callback: GetContentInfoCallback)

    interface GetDetailsCallback {

        fun onSuccess(details: ArrayList<Detail>)

        fun onFailure(throwable: Throwable)

        fun onNetworkFailure()

    }

    abstract fun getDetails(ref: String, callback: GetDetailsCallback)
}