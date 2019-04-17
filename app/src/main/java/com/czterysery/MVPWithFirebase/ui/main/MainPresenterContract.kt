package com.czterysery.MVPWithFirebase.ui.main

import androidx.appcompat.widget.Toolbar
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation

/**
 * Created by tmax0 on 29.12.2017.
 */
interface MainPresenterContract {

    fun initToolbar(toolbar: Toolbar)

    fun initNavItems(bottomNav: AHBottomNavigation)

    fun initNavCallback(bottomNav: AHBottomNavigation)

    fun isToolbarVisible(toolbar: Toolbar, view: Boolean)

    fun isBottomNavigationVisible(bottomNav: AHBottomNavigation, view: Boolean)
}