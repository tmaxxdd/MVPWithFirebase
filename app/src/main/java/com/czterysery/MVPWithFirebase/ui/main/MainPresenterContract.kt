package com.czterysery.MVPWithFirebase.ui.main

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation

/**
 * Created by tmax0 on 29.12.2017.
 */
interface MainPresenterContract {

    fun initNavItems(bottomNav: AHBottomNavigation)

    fun initNavCallback(bottomNav: AHBottomNavigation)

}