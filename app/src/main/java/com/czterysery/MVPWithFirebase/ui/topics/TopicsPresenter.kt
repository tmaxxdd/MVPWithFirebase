package com.czterysery.MVPWithFirebase.ui.topics

import android.content.Context
import android.util.Log
import com.czterysery.MVPWithFirebase.R
import com.czterysery.MVPWithFirebase.data.DataRepository
import com.czterysery.MVPWithFirebase.data.DataSource
import com.czterysery.MVPWithFirebase.data.models.Topic
import com.czterysery.MVPWithFirebase.util.mvp.BasePresenter

/**
 * Created by tmax0 on 24.12.2017.
 */
class TopicsPresenter: BasePresenter<TopicsContract.View>, TopicsContract.Presenter {

    private val TAG = javaClass.simpleName
    private val dataRepository: DataRepository

    constructor(view: TopicsContract.View, dataRepository: DataRepository){
        this.dataRepository = dataRepository
        this.view = view
    }

    override fun getTopics(context: Context, ref: String) {

        if (view == null)
            return

        view!!.setProgressBar(true)

        dataRepository.getTopics(context, ref, object : DataSource.GetTopicsCallback {

            override fun onSuccess(topics: ArrayList<Topic>) {
                if (view != null) {
                    Log.d(TAG, "Received data: $topics")
                    view!!.showTopics(topics)
                    view!!.setProgressBar(false)
                    Log.d(TAG, "onSuccess")
                }
            }

            override fun onFailure(throwable: Throwable) {
                if (view != null) {
                    view!!.setProgressBar(false)
                    view!!.showToast(context.getString(R.string.error_msg))
                    Log.d(TAG, "onFailure")
                }
            }

            override fun onNetworkFailure() {
                if (view != null) {
                    view!!.setProgressBar(false)
                    view!!.showToast(context.getString(R.string.network_failure_msg))
                    Log.d(TAG, "onNetworkFailure")
                }
            }
        })
    }
}