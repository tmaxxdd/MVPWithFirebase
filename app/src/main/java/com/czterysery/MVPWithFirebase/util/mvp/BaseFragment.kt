package com.czterysery.MVPWithFirebase.util.mvp

import androidx.fragment.app.Fragment
import org.jetbrains.anko.support.v4.toast

/*
    BaseFragment is an abstract class which should be
    inherited from any fragment in app.
    This class contains methods that are universal for all views.
 */
abstract class BaseFragment : Fragment(), IBaseFragment {

    //This can be implemented here thanks to Anko Toasts
    override fun showToast(message: String) {
        toast(message)
    }

    //This must be implemented in an inheritance class
    override fun setProgressBar(visible: Boolean) {}

}