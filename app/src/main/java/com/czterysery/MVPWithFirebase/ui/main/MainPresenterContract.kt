package com.czterysery.MVPWithFirebase.ui.main

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation

interface MainPresenterContract {
    fun initNavItems(bottomNav: AHBottomNavigation)
    fun initNavCallback(bottomNav: AHBottomNavigation)
}