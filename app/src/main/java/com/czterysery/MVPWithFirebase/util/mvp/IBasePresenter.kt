package com.czterysery.MVPWithFirebase.util.mvp

/*
    Definition of basic view's methods.
 */
interface IBasePresenter<in ViewT> {

    fun onViewActive(view: ViewT)

    fun onViewInactive()

}