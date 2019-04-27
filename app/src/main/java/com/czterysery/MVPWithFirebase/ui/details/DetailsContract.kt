package com.czterysery.MVPWithFirebase.ui.details

import android.content.Context
import com.czterysery.MVPWithFirebase.data.models.Detail
import com.czterysery.MVPWithFirebase.util.mvp.IBasePresenter
import com.czterysery.MVPWithFirebase.util.mvp.IBaseFragment

/**
 * Created by tmax0 on 07.01.2018.
 */
//TODO Refactor
interface DetailsContract {

    interface Fragment : IBaseFragment {

        fun showDetails(cards: ArrayList<Detail>)

        fun shouldShowPlaceholderText()

    }

    interface Presenter: IBasePresenter<Fragment> {

        fun getDetails(context: Context, name: String)

    }
}