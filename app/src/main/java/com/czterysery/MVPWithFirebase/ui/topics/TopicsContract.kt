package com.czterysery.MVPWithFirebase.ui.topics

import android.content.Context
import com.czterysery.MVPWithFirebase.data.models.Topic
import com.czterysery.MVPWithFirebase.util.mvp.IBasePresenter
import com.czterysery.MVPWithFirebase.util.mvp.IBaseView

/*
    First of all a presenter is responsible for handling
    data's callback from DataRepository and transferring
    the data to a view in a suitable form.
 */
interface TopicsContract {

    interface View : IBaseView {

        fun showTopics(cards: ArrayList<Topic>)

    }

    interface Presenter: IBasePresenter<View> {

        fun getTopics(context: Context, ref: String)

    }
}