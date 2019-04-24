package com.czterysery.MVPWithFirebase.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.czterysery.MVPWithFirebase.R
import com.czterysery.MVPWithFirebase.util.BaseFragmentInteractionListener
import com.czterysery.MVPWithFirebase.util.FOABaseActivity
import kotlinx.android.synthetic.main.activity_main.*

/*
 * MainActivity is a base for other fragments.
 * Here you should only init. Toolbar and BottomNavigation.
 */
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

    //BaseFragmentInteractionListener
    override fun setToolbarVisible(visible: Boolean) {
        if (visible)
            toolbar.visibility = View.VISIBLE
        else
            toolbar.visibility = View.GONE
    }

    //BaseFragmentInteractionListener
    override fun setBottomNavigationVisible(visible: Boolean) {
        if (visible)
            bottomNav.visibility = View.VISIBLE
        else
            bottomNav.visibility = View.GONE
    }

    //Invoke a method in the FOABaseActivity
    override fun <T : Fragment> showFragment(fragmentClass: Class<T>, bundle: Bundle?,
                                             addToBackStack: Boolean?, tag: String) {
        showFragment(fragmentClass, bundle, addToBackStack!!, tag)
    }

    override fun initializeToolbar() {
        setSupportActionBar(toolbar)
        //Needed to center toolbar's title
        supportActionBar!!.setDisplayShowTitleEnabled(false)
    }

    //Only configure base functionality. Do rest in presenter.
    override fun initializeBottomNav() {
        bottomNav.apply {
            isBehaviorTranslationEnabled = true
            isTranslucentNavigationEnabled = true
            accentColor = R.color.colorAccent
            isColored = true
        }

        presenter.initNavItems(bottomNav)
        presenter.initNavCallback(bottomNav)
    }

}