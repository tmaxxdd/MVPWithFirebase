package com.czterysery.MVPWithFirebase.ui.details

import com.czterysery.MVPWithFirebase.data.models.Detail
import com.czterysery.MVPWithFirebase.util.mvp.IBaseFragment
import com.czterysery.MVPWithFirebase.util.mvp.IBasePresenter

/*
    DetailsContract defines functions for
    displaying data in DetailsFragment.
 */
interface DetailsContract {

    interface Fragment : IBaseFragment {

        fun showDetails(cards: ArrayList<Detail>)

    }

    interface Presenter: IBasePresenter<Fragment> {

        fun getDetails(name: String)

    }
}