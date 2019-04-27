package com.czterysery.MVPWithFirebase.ui.topics

import android.util.Log
import com.czterysery.MVPWithFirebase.R
import com.czterysery.MVPWithFirebase.data.DataRepository
import com.czterysery.MVPWithFirebase.data.DataSource
import com.czterysery.MVPWithFirebase.data.models.Topic
import com.czterysery.MVPWithFirebase.util.mvp.BasePresenter


/*
    First of all a presenter is responsible for handling
    data's callback from DataRepository and transferring
    the data to a view in a suitable form.
 */
//TODO Refactor BasePresenter and BaseFragment
class TopicsPresenter() :
        BasePresenter<TopicsContract.Fragment>(), TopicsContract.Presenter {
    private val TAG = javaClass.simpleName
    private lateinit var dataRepository: DataRepository

    constructor(view: TopicsContract.Fragment, dataRepository: DataRepository) : this() {
        this.dataRepository = dataRepository
        this.view = view
    }

    override fun getTopics(ref: String) {

        if (view == null) return //Cannot access view

        view!!.setProgressBar(true)

        dataRepository.getTopics(ref, object : DataSource.GetTopicsCallback {

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