package com.czterysery.MVPWithFirebase.util

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
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
        //This class will manage fragments and theirs communication
        supportFragmentManager.addOnBackStackChangedListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            //Will remove fragment if back arrow is clicked
            android.R.id.home -> { popFragmentBackStack(); return true }
            R.id.github_action -> {
                val action = Intent(Intent.ACTION_VIEW)
                action.data = Uri.parse("https://github.com/tmaxxdd/MVPWithFirebase")
                startActivity(action)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(isMainMenuVisible()) {
            //In main window
            menuInflater.inflate(R.menu.menu_main, menu)
        } else {
            //In other windows
            menuInflater.inflate(R.menu.clean_menu, menu)
        }

        return true
    }

    override fun onBackStackChanged() {
        shouldShowActionBarUpButton()
    }

    /*
        Public accessible method. A bridge to the private showFragmentOrCreate.
        Definition of this method is in the BaseFragmentInteractionListener.
     */
    fun <T: Fragment> showFragment(fragmentClass: Class<T>, bundle: Bundle? = null,
                                   addToBackStack: Boolean = false, tag: String) {
        showFragmentOrCreate(fragmentClass, bundle, addToBackStack, tag)
    }

    //This function changes currently visible fragment or create new if any cannot be find by a tag.
    private fun <T: Fragment> showFragmentOrCreate(fragmentClass: Class<T>, bundle: Bundle? = null,
                                                   addToBackStack: Boolean = false, tag: String) {
        //Start dealing with fragments
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        //Create a fragment instance from a tag of given param (fragmentClass)
        var fragment: Fragment? = supportFragmentManager.findFragmentByTag(
                tag)

        // Cannot find by tag. Create instance.
        if (fragment == null) {
            try {
                fragment = fragmentClass.newInstance()
                fragment?.arguments = bundle
                Log.d(TAG, "Create new Fragment instance")
            } catch (e: Throwable) {
                throw RuntimeException("New Fragment should have been created", e)
            } catch (e: IllegalAccessException) {
                throw RuntimeException("New Fragment should have been created", e)
            }
        }

        //Show fragment in placeholder
        fragment?.let {
            fragmentTransaction.replace(R.id.fragment_placeholder,
                    it, tag)
        }

        if (addToBackStack){
            fragmentTransaction.addToBackStack(null)
        }

        fragmentTransaction.commit()
    }

    //Removes a fragment from a back stack
    private fun popFragmentBackStack() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        }
    }

    private fun shouldShowActionBarUpButton() {
        if (isMainMenuVisible()) {
            //There is no fragment in a stack. Main fragment is displayed
            //Don't show back arrow icon
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        } else {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun isMainMenuVisible(): Boolean {
        return supportFragmentManager.backStackEntryCount == 0
    }

}