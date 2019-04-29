package com.czterysery.MVPWithFirebase.util.mvp

/*
    Definition of completely basic methods for
    all fragments in the app.
 */
interface IBaseFragment {

    fun showToast(message: String)

    fun setProgressBar(visible: Boolean)

}