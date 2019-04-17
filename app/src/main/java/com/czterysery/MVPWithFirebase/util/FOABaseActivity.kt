package com.czterysery.MVPWithFirebase.util

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.czterysery.MVPWithFirebase.R

/**
 * This class is responsible for showing and destroying {@link Fragment}
 * and handling back and up navigation. Based on
 * Fragment Oriented Architecture explained here
 * http://vinsol.com/blog/2014/09/15/advocating-fragment-oriented-applications-in-android/
 */
abstract class FOABaseActivity : AppCompatActivity(), FragmentManager.OnBackStackChangedListener {
    private val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        supportFragmentManager.addOnBackStackChangedListener(this)
    }

    /**
     * Generic function showFragment() allows to pass any Fragment class and show it in Ui
     *
     * @param fragmentClass  is an any fragment to show
     * @param bundle         the data that should be passed with fragment
     * @param addToBackStack ...
     */

    @SuppressLint("PrivateResource")
    private fun <T: Fragment> showFragment(fragmentClass: Class<T>, bundle: Bundle? = null, addToBackStack: Boolean = false) {
        Log.d(TAG, "showFragment")
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        var fragment: Fragment? = supportFragmentManager.findFragmentByTag(
                fragmentClass.simpleName)

        fragment?.let {
            fragment = null
        }

        //If can't find the fragment by simpleName.
        // Then create it.
        if (fragment == null) {
            try {
                fragment = fragmentClass.newInstance()
                fragment?.arguments = bundle
            } catch (e: Throwable) {
                throw RuntimeException("New Fragment should have been created", e)
            } catch (e: IllegalAccessException) {
                throw RuntimeException("New Fragment should have been created", e)
            }
        }

        //Here can we create transaction animation

        //fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
                //R.anim.slide_in_right, R.anim.slide_out_left)
        fragment?.let {
            fragmentTransaction.replace(R.id.fragment_placeholder,
                    it, fragmentClass.simpleName)
        }

        if (addToBackStack){
            fragmentTransaction.addToBackStack(null)
        }

        fragmentTransaction.commit()
    }

    fun <T: Fragment> showFragment(fragmentClass: Class<T>) {
        showFragment(fragmentClass, null, false)
    }

    fun <T: Fragment> showFragment(fragmentClass: Class<T>, bundle: Bundle) {
        showFragment(fragmentClass, bundle, true)
    }

    private fun popFragmentBackStack() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        }
    }

    private fun shouldShowActionBarUpButton() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        } else {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {popFragmentBackStack(); return true}
            R.id.github_action -> {
                val action = Intent(Intent.ACTION_VIEW)
                action.data = Uri.parse("")
                startActivity(action)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onBackStackChanged() {
        shouldShowActionBarUpButton()
    }

}