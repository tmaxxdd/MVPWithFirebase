package com.czterysery.MVPWithFirebase.ui.content

import android.content.Context
import com.czterysery.MVPWithFirebase.R
import com.czterysery.MVPWithFirebase.data.DataRepository
import com.czterysery.MVPWithFirebase.data.DataSource
import com.czterysery.MVPWithFirebase.data.models.Content
import com.czterysery.MVPWithFirebase.data.models.ContentInfo
import com.czterysery.MVPWithFirebase.util.mvp.BasePresenter

/**
 * Created by tmax0 on 05.01.2018.
 */
class ContentPresenter: BasePresenter<ContentContract.View>, ContentContract.Presenter  {
    private val TAG = javaClass.simpleName

    private val dataRepository: DataRepository
    constructor(view: ContentContract.View, dataRepository: DataRepository){
        this.dataRepository = dataRepository
        this.view = view
    }

    override fun getContent(context: Context, name: String) {

        if (view == null)
            return

        view?.setProgressBar(true)

        dataRepository.getContent(context, name, object : DataSource.GetContentCallback {

            override fun onSuccess(contents: ArrayList<Content>) {
                if (view != null){
                    view!!.showContent(contents)
                    view!!.setProgressBar(false)
                }
            }

            override fun onFailure(throwable: Throwable) {
                if (view != null){
                    view!!.setProgressBar(false)
                    view!!.showToast(context.getString(R.string.error_msg))
                }
            }

            override fun onNetworkFailure() {
                if (view != null){
                    view!!.setProgressBar(false)
                    view!!.showToast(context.getString(R.string.network_failure_msg))
                }
            }

        })
    }

    override fun getContentInfo(context: Context, name: String) {

        dataRepository.getContentInfo(context, name, object : DataSource.GetContentInfoCallback {

            override fun onSuccess(info: ContentInfo) {
                if (view != null) {
                    view!!.setProgressBar(false)
                    info.image?.let { view!!.showImage(it) }
                    info.name?.let { view!!.showTitle(it) }
                    info.description?.let { view!!.showDescription(it) }
                }
            }

            override fun onFailure(throwable: Throwable) {
                if (view != null) {
                    view!!.setProgressBar(false)
                    view!!.showToast(context.getString(R.string.error_msg))
                }
            }

            override fun onNetworkFailure() {
                if (view != null) {
                    view!!.setProgressBar(false)
                    view!!.showToast(context.getString(R.string.network_failure_msg))
                }
            }

        })

    }

}