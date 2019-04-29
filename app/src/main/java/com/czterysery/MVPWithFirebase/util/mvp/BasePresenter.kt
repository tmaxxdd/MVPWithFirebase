package com.czterysery.MVPWithFirebase.util.mvp

/*
    BasePresenter contains an information if
    view (ViewT) is available or not.
    Fragment (View) manages its state.
 */
abstract class BasePresenter<ViewT>: IBasePresenter<ViewT> {

    protected var view: ViewT? = null

    override fun onViewActive(view: ViewT) {
        this.view = view
    }

    override fun onViewInactive() {
        view = null
    }

}