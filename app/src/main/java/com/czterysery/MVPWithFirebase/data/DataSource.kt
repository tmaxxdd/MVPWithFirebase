package com.czterysery.MVPWithFirebase.data

import android.content.Context
import com.czterysery.MVPWithFirebase.data.models.Content
import com.czterysery.MVPWithFirebase.data.models.ContentInfo
import com.czterysery.MVPWithFirebase.data.models.Detail
import com.czterysery.MVPWithFirebase.data.models.Topic

/**
 * Created by tmax0 on 24.12.2017.
 */
abstract class DataSource {

    interface GetTopicsCallback {

        fun onSuccess(topics: ArrayList<Topic>)

        fun onFailure(throwable: Throwable)

        fun onNetworkFailure()
    }

    abstract fun getTopics(context: Context, ref: String, callback: GetTopicsCallback)

    interface GetContentCallback {

        fun onSuccess(contents: ArrayList<Content>)

        fun onFailure(throwable: Throwable)

        fun onNetworkFailure()

    }

    abstract fun getContent(context: Context, ref: String, callback: GetContentCallback)

    interface GetContentInfoCallback {

        fun onSuccess(info: ContentInfo)

        fun onFailure(throwable: Throwable)

        fun onNetworkFailure()

    }

    abstract fun getContentInfo(context: Context, ref: String, callback: GetContentInfoCallback)

    interface GetDetailsCallback {

        fun onSuccess(details: ArrayList<Detail>)

        fun onFailure(throwable: Throwable)

        fun onNetworkFailure()

    }

    abstract fun getDetails(context: Context, ref: String, callback: GetDetailsCallback)
}