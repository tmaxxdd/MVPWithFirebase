package com.czterysery.MVPWithFirebase.ui.details

import android.content.Context
import android.util.Log
import com.czterysery.MVPWithFirebase.R
import com.czterysery.MVPWithFirebase.data.DataRepository
import com.czterysery.MVPWithFirebase.data.DataSource
import com.czterysery.MVPWithFirebase.data.models.Detail
import com.czterysery.MVPWithFirebase.util.mvp.BasePresenter

/**
 * Created by tmax0 on 08.01.2018.
 */
class DetailsPresenter : BasePresenter<DetailsContract.Fragment>, DetailsContract.Presenter  {
    private val TAG = javaClass.simpleName
    private val dataRepository: DataRepository

    constructor(view: DetailsContract.Fragment, dataRepository: DataRepository){
        this.dataRepository = dataRepository
        this.view = view
    }

    override fun getDetails(context: Context, name: String) {

        if (view == null)
            return

        view?.setProgressBar(true)

        dataRepository.getDetails(context, name, object : DataSource.GetDetailsCallback {

            override fun onSuccess(details: ArrayList<Detail>) {
                if (view != null) {
                    view!!.showDetails(details)
                    view!!.setProgressBar(false)
                    view!!.shouldShowPlaceholderText()
                    Log.d(TAG, "onSuccess")
                }
            }

            override fun onFailure(throwable: Throwable) {
                if (view != null) {
                    view!!.setProgressBar(false)
                    view!!.showToast(context.getString(R.string.error_msg))
                    view!!.shouldShowPlaceholderText()
                    Log.d(TAG, "onFailure")
                }
            }

            override fun onNetworkFailure() {
                if (view != null) {
                    view!!.setProgressBar(false)
                    view!!.showToast(context.getString(R.string.network_failure_msg))
                    view!!.shouldShowPlaceholderText()
                    Log.d(TAG, "onNetworkFailure")
                }
            }
        })

    }
}