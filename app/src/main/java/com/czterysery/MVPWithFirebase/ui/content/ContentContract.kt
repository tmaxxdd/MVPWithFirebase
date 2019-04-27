package com.czterysery.MVPWithFirebase.ui.content

import android.content.Context
import com.czterysery.MVPWithFirebase.data.models.Content
import com.czterysery.MVPWithFirebase.util.mvp.IBasePresenter
import com.czterysery.MVPWithFirebase.util.mvp.IBaseFragment

/**
 * Created by tmax0 on 05.01.2018.
 */
//TODO Refactor
interface ContentContract {

    interface Fragment : IBaseFragment {

        fun showImage(source: String)

        fun showTitle(title: String)

        fun showDescription(text: String)

        fun showContent(contents: ArrayList<Content>)
    }

    interface Presenter: IBasePresenter<Fragment> {

        fun getContent(ref: String)

        fun getContentInfo(ref: String)

    }
}