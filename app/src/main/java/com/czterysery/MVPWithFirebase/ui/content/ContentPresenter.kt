package com.czterysery.MVPWithFirebase.ui.content

import com.czterysery.MVPWithFirebase.data.DataRepository
import com.czterysery.MVPWithFirebase.data.DataSource
import com.czterysery.MVPWithFirebase.data.models.Content
import com.czterysery.MVPWithFirebase.data.models.ContentInfo
import com.czterysery.MVPWithFirebase.util.mvp.BasePresenter

/*
    Content presenter handles callback for list of cards in ContentFragment
    and additional data like image and title of selected topic.
 */
class ContentPresenter(private val dataRepository: DataRepository): BasePresenter<ContentContract.Fragment>, ContentContract.Presenter  {
    private val TAG = javaClass.simpleName

    override fun getContent(ref: String) {

        //Show loading
        view?.setProgressBar(true)

        dataRepository.getContent(ref, object : DataSource.GetContentCallback {

            override fun onSuccess(contents: ArrayList<Content>) {
                view?.let {
                    it.showContent(contents)
                    it.setProgressBar(false)
                }
            }

            override fun onFailure(throwable: Throwable) {
                view?.let{
                    it.setProgressBar(false)
                    it.showToast("App wasn't able to retrieve data")
                }
            }

            override fun onNetworkFailure() {
                view?.let{
                    it.setProgressBar(false)
                    it.showToast("App wasn't able to connect to the internet")
                }
            }

        })
    }

    override fun getContentInfo(ref: String) {

        dataRepository.getContentInfo(ref, object : DataSource.GetContentInfoCallback {

            override fun onSuccess(info: ContentInfo) {
                view?.let{ view ->
                    view.setProgressBar(false)
                    info.image?.let { image -> view.showImage(image) }
                    info.name?.let { name -> view.showTitle(name) }
                    info.description?.let { desc -> view.showDescription(desc) }
                }
            }

            override fun onFailure(throwable: Throwable) {
                view?.let{
                    it.setProgressBar(false)
                    it.showToast("App wasn't able to retrieve data")
                }
            }

            override fun onNetworkFailure() {
                view?.let{
                    it.setProgressBar(false)
                    it.showToast("App wasn't able to connect to the internet")
                }
            }

        })

    }

}