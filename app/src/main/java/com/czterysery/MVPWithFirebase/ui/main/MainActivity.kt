package com.czterysery.MVPWithFirebase.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.czterysery.MVPWithFirebase.R
import com.czterysery.MVPWithFirebase.util.BaseFragmentInteractionListener
import com.czterysery.MVPWithFirebase.util.FOABaseActivity
import kotlinx.android.synthetic.main.activity_main.*

/*
 * MainActivity is a frame for other fragments.
 * Here you should init. only your views.
 */

//All 'AppCompat' methods are in FOABaseActivity
class MainActivity : FOABaseActivity(), MainContract, BaseFragmentInteractionListener {
    private val TAG = javaClass.simpleName

    //https://kotlinlang.org/docs/reference/delegated-properties.html#lazy
    private val toolbar by lazy {
        main_toolbar //From layout
    }

    private val bottomNav by lazy {
        main_bottom_navigation //From layout
    }

    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainPresenter(this)

        initializeToolbar()
        initializeBottomNav()

    }

    private fun initializeToolbar() {
        setSupportActionBar(toolbar)
        //Needed to center toolbar's title
        supportActionBar!!.setDisplayShowTitleEnabled(false)
    }

    private fun initializeBottomNav() {
        bottomNav.apply {
            isBehaviorTranslationEnabled = true
            isTranslucentNavigationEnabled = true
            accentColor = R.color.colorAccent
            isColored = true
        }
        presenter.initNavItems(bottomNav)
        presenter.initNavCallback(bottomNav)
    }

    override fun <T : Fragment> showFragment(fragmentClass: Class<T>, bundle: Bundle, addToBackStack: Boolean) {
        showFragment(fragmentClass, bundle)
    }

    override fun setToolbar(view: Boolean) {
        presenter.isToolbarVisible(toolbar, view)
    }

    override fun setBottomNavigation(view: Boolean) {
        presenter.isBottomNavigationVisible(bottomNav, view)
    }

}