package com.czterysery.MVPWithFirebase.ui.content

import com.czterysery.MVPWithFirebase.data.models.Content
import com.czterysery.MVPWithFirebase.util.mvp.IBaseFragment
import com.czterysery.MVPWithFirebase.util.mvp.IBasePresenter

/*
    This interface provides the functions that
    ContentPresenter musts implements.
 */
interface ContentContract {

    // View
    interface Fragment : IBaseFragment {

        fun showImage(source: String)

        fun showTitle(title: String)

        fun showDescription(text: String)

        fun showContent(contents: ArrayList<Content>)
    }

    // Presenter
    interface Presenter: IBasePresenter<Fragment> {

        fun getContent(ref: String)

        fun getContentInfo(ref: String)

    }
}
