package com.czterysery.MVPWithFirebase.ui.content

import android.content.Context
import com.czterysery.MVPWithFirebase.data.models.Content
import com.czterysery.MVPWithFirebase.util.mvp.IBasePresenter
import com.czterysery.MVPWithFirebase.util.mvp.IBaseView

/**
 * Created by tmax0 on 05.01.2018.
 */
interface ContentContract {

    interface View : IBaseView {

        fun showImage(source: String)

        fun showTitle(title: String)

        fun showDescription(text: String)

        fun showContent(contents: ArrayList<Content>)
    }

    interface Presenter: IBasePresenter<View> {

        fun getContent(context: Context, name: String)

        fun getContentInfo(context: Context, name: String)

    }
}