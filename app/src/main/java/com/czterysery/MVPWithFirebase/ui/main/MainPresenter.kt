package com.czterysery.MVPWithFirebase.ui.main

import android.os.Bundle
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.czterysery.MVPWithFirebase.util.constants.DataType
import com.czterysery.MVPWithFirebase.util.constants.FragmentType
import com.czterysery.MVPWithFirebase.R
import com.czterysery.MVPWithFirebase.ui.topics.TopicsFragment
import com.czterysery.MVPWithFirebase.util.constants.FirebaseRef

/*
    This presenter is only responsible for adjusting bottom navigation.
    The more complex configuration is moved here only for better MainActivity's readability.
 */

class MainPresenter(private val mainView: MainActivity) : MainPresenterContract {
    private val TAG = javaClass.simpleName

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
            accentColor = resources.getColor(R.color.colorAccent)
            inactiveColor = resources.getColor(R.color.inactiveColor)
            isForceTint = true
            isTranslucentNavigationEnabled = true
            isBehaviorTranslationEnabled = true
        }
    }

    override fun initNavCallback(bottomNav: AHBottomNavigation) {
        bottomNav.setOnTabSelectedListener { position, _ ->
            when(position) {
                0 -> showTopicFragment(FirebaseRef.rootLearn, FragmentType.TAG_TOPIC_LEARN)
                1 -> showTopicFragment(FirebaseRef.rootPlan, FragmentType.TAG_TOPIC_PLAN)
                2 -> showTopicFragment(FirebaseRef.rootWork, FragmentType.TAG_TOPIC_WORK)
                else -> showTopicFragment(FirebaseRef.rootLearn, FragmentType.TAG_TOPIC_LEARN)
            }
        }

        //Show first fragment for default
        bottomNav.currentItem = 0
    }

    private fun showTopicFragment(ref: String, tag: String): Boolean {
        val bundle = Bundle()
        bundle.putString(DataType.BUNDLE_TOPIC, ref)
        mainView.showFragment(TopicsFragment::class.java, bundle, false, tag)
        return true
    }

}
