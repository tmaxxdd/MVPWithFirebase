package com.czterysery.MVPWithFirebase.ui.topics

import android.content.Context
import com.czterysery.MVPWithFirebase.data.models.Topic
import com.czterysery.MVPWithFirebase.util.mvp.IBasePresenter
import com.czterysery.MVPWithFirebase.util.mvp.IBaseView

/**
 * Created by tmax0 on 22.12.2017.
 */
interface TopicsContract {

    interface View : IBaseView {

        fun showTopics(cards: ArrayList<Topic>)

    }

    interface Presenter: IBasePresenter<View> {

        fun getTopics(context: Context, ref: String)

    }
}