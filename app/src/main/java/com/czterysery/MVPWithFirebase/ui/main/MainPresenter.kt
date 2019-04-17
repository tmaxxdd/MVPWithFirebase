package com.czterysery.MVPWithFirebase.ui.main

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.Toolbar
import android.view.View
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.czterysery.MVPWithFirebase.Constants
import com.czterysery.MVPWithFirebase.R
import com.czterysery.MVPWithFirebase.ui.topics.TopicsFragment
import com.czterysery.MVPWithFirebase.util.FirebaseRefUtil

/**
 * Created by tmax0 on 29.12.2017.
 */
class MainPresenter(private val mainView: MainActivity): MainPresenterContract {

    override fun initToolbar(toolbar: Toolbar) {
        mainView.setSupportActionBar(toolbar)
        mainView.supportActionBar?.apply {
            //Adjust toolbar here
        }
    }

    @SuppressLint("PrivateResource", "ResourceType")
    override fun initNavItems(bottomNav: AHBottomNavigation) {
        val itemTopics = AHBottomNavigationItem(R.string.learn,
                R.drawable.ic_explore_white_24dp, R.color.white)
        val itemPlans = AHBottomNavigationItem(R.string.plan,
                R.drawable.ic_timer_white_24dp, R.color.white)
        val itemActions = AHBottomNavigationItem(R.string.work,
                R.drawable.ic_playlist_add_check_white_24dp, R.color.white)

        bottomNav.apply{
            addItem(itemTopics)
            addItem(itemPlans)
            addItem(itemActions)
            accentColor = Color.parseColor("#536DFE")
            inactiveColor = Color.parseColor("#9E9E9E")
            isForceTint = true
            isTranslucentNavigationEnabled = true
            isBehaviorTranslationEnabled = true
        }
    }

    override fun initNavCallback(bottomNav: AHBottomNavigation) {
        bottomNav.setOnTabSelectedListener { position, _ ->
            when(position) {
                0 -> showFragmentAndReference(TopicsFragment::class.java,  FirebaseRefUtil.rootLearn)
                1 -> showFragmentAndReference(TopicsFragment::class.java, FirebaseRefUtil.rootPlan)
                2 -> showFragmentAndReference(TopicsFragment::class.java, FirebaseRefUtil.rootWork)
                else -> showFragmentAndReference(TopicsFragment::class.java, FirebaseRefUtil.rootWork)
            }
        }
        bottomNav.currentItem = 0
    }

    override fun isToolbarVisible(toolbar: Toolbar, view: Boolean) {
        if (view)
            toolbar.visibility = View.VISIBLE
        else
            toolbar.visibility = View.GONE
    }

    override fun isBottomNavigationVisible(bottomNav: AHBottomNavigation, view: Boolean) {
        if (view)
            bottomNav.visibility = View.VISIBLE
        else
            bottomNav.visibility = View.GONE
    }

    private fun <T: Fragment> showFragmentAndReference(fragmentClass: Class<T>, ref: String): Boolean {
        val bundle = Bundle()
        bundle.putString(Constants.BUNDLE_TOPIC, ref)
        mainView.showFragment(fragmentClass, bundle)
        return true
    }


}
