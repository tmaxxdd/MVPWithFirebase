package com.czterysery.MVPWithFirebase.ui.details

import android.content.Context
import com.czterysery.MVPWithFirebase.data.models.Detail
import com.czterysery.MVPWithFirebase.util.mvp.IBasePresenter
import com.czterysery.MVPWithFirebase.util.mvp.IBaseView

/**
 * Created by tmax0 on 07.01.2018.
 */
interface DetailsContract {

    interface View : IBaseView {

        fun showDetails(cards: ArrayList<Detail>)

        fun shouldShowPlaceholderText()

    }

    interface Presenter: IBasePresenter<View> {

        fun getDetails(context: Context, name: String)

    }
}