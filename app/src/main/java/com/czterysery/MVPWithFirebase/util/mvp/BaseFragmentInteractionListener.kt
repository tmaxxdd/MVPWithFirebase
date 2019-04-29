package com.czterysery.MVPWithFirebase.util.mvp

import android.os.Bundle
import androidx.fragment.app.Fragment

/*
    Defines the interface for managing navigation elements.
 */
interface BaseFragmentInteractionListener {

    fun setToolbarVisible(visible: Boolean)

    fun setBottomNavigationVisible(visible: Boolean)

    fun <T: Fragment> showFragment(fragmentClass: Class<T>, bundle: Bundle?,
                                                         addToBackStack: Boolean?, tag: String)
}