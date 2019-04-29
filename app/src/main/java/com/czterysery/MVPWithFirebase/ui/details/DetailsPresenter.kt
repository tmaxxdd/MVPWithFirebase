package com.czterysery.MVPWithFirebase.ui.details

import com.czterysery.MVPWithFirebase.data.DataRepository
import com.czterysery.MVPWithFirebase.data.DataSource
import com.czterysery.MVPWithFirebase.data.models.Detail
import com.czterysery.MVPWithFirebase.util.mvp.BasePresenter

/*
    DetailsPresenter request and receive the most detailed data from
    DataRepository and shows it thanks to DetailsFragment
 */
class DetailsPresenter(private val dataRepository: DataRepository) :
        BasePresenter<DetailsContract.Fragment>(), DetailsContract.Presenter  {
    private val TAG = javaClass.simpleName

    override fun getDetails(name: String) {

        view?.setProgressBar(true)

        dataRepository.getDetails(name, object : DataSource.GetDetailsCallback {

            override fun onSuccess(details: ArrayList<Detail>) {
                view?.let{
                    it.showDetails(details)
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
}