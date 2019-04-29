package com.czterysery.MVPWithFirebase.ui.topics

import com.czterysery.MVPWithFirebase.data.DataRepository
import com.czterysery.MVPWithFirebase.data.DataSource
import com.czterysery.MVPWithFirebase.data.models.Topic
import com.czterysery.MVPWithFirebase.util.mvp.BasePresenter


/*
    First of all the TopicsPresenter is responsible for handling
    data's callback from DataRepository and transferring
    the data to a view in a suitable form.
 */
class TopicsPresenter(private val dataRepository: DataRepository) :
        BasePresenter<TopicsContract.Fragment>(), TopicsContract.Presenter {
    private val TAG = javaClass.simpleName

    override fun getTopics(ref: String) {

        view?.setProgressBar(true)

        dataRepository.getTopics(ref, object : DataSource.GetTopicsCallback {

            override fun onSuccess(topics: ArrayList<Topic>) {
                view?.let {
                    it.showTopics(topics)
                    it.setProgressBar(false)
                }
            }

            override fun onFailure(throwable: Throwable) {
                view?.let {
                    it.setProgressBar(false)
                    it.showToast("App wasn't able to retrieve data")
                }
            }

            override fun onNetworkFailure() {
                view?.let {
                    it.setProgressBar(false)
                    it.showToast("App wasn't able to connect to the internet")
                }
            }
        })
    }
}