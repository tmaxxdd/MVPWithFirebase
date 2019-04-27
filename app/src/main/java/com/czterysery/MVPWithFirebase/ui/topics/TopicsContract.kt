package com.czterysery.MVPWithFirebase.ui.topics

import com.czterysery.MVPWithFirebase.data.models.Topic
import com.czterysery.MVPWithFirebase.util.mvp.IBasePresenter
import com.czterysery.MVPWithFirebase.util.mvp.IBaseFragment

/*
    Topic is only responsible for showing cards with given topics.
 */
interface TopicsContract {

    // View
    interface Fragment : IBaseFragment {

        // Show list of cards
        fun showTopics(cards: ArrayList<Topic>)

    }

    // Presenter
    interface Presenter: IBasePresenter<Fragment> {

        // Retrieve an Arraylist with card's items
        fun getTopics(ref: String)

    }
}