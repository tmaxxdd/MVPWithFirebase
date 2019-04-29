package com.czterysery.MVPWithFirebase.ui.content

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.brouding.simpledialog.SimpleDialog
import com.czterysery.MVPWithFirebase.*
import com.czterysery.MVPWithFirebase.data.DataRepository
import com.czterysery.MVPWithFirebase.data.local.LocalDataSource
import com.czterysery.MVPWithFirebase.data.models.Content
import com.czterysery.MVPWithFirebase.data.remote.RemoteDataSource
import com.czterysery.MVPWithFirebase.ui.details.DetailsFragment
import com.czterysery.MVPWithFirebase.util.constants.DataType
import com.czterysery.MVPWithFirebase.util.constants.FragmentType
import com.czterysery.MVPWithFirebase.util.helpers.GsonHelper
import com.czterysery.MVPWithFirebase.util.helpers.NetworkHelper
import com.czterysery.MVPWithFirebase.util.helpers.SharedPrefsHelper
import com.czterysery.MVPWithFirebase.util.mvp.BaseFragment
import com.czterysery.MVPWithFirebase.util.mvp.BaseFragmentInteractionListener
import kotlinx.android.synthetic.main.fragment_content.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/*
    ContentFragment is a class that handles list of subtopics.
    Elements are placed in 2-row grid.
 */

class ContentFragment: BaseFragment(), ContentContract.Fragment {
    private val TAG = javaClass.simpleName
    private val contentsList = ArrayList<Content>()
    private lateinit var fragmentInteractionListener: BaseFragmentInteractionListener
    private lateinit var presenter: ContentPresenter

    private val dataRepository by lazy {
        //First invocation is in the onCreate, so we are sure that activity has been instantiated
        DataRepository(
                RemoteDataSource(),
                LocalDataSource(GsonHelper(SharedPrefsHelper(activity!!.applicationContext))),
                NetworkHelper(activity!!.applicationContext))
    }

    //RecyclerView callback
    private val gridAdapter by lazy {
        ContentRecyclerAdapter(contentsList){
            //On card click
            //Go to the more detailed fragment
            showDetailsFragment(it.name)
        }
    }

    //Simple dialog with topic description
    private val descDialog by lazy {
        //See https://github.com/BROUDING/SimpleDialog
        SimpleDialog.Builder(this.context!!)
                .setTitle(resources.getString(R.string.description_label))
                .setCancelable(true)
                .setBtnConfirmText("OK")
                .setBtnConfirmTextColor(R.color.colorAccent)
                .onConfirm { dialog, _ ->
                    dialog.dismiss()
                }
    }

    //Name of main topic
    private val contentName by lazy {
        arguments?.get(DataType.BUNDLE_CONTENT).toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = ContentPresenter(dataRepository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container!!.inflate(R.layout.fragment_content)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        initRecyclerView()

        //Show topic's description
        content_fab.onClick { descDialog.show() }

        getContentInfo()
        getContent()

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentInteractionListener = activity as BaseFragmentInteractionListener
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "$TAG detached.")
    }

    override fun onResume() {
        super.onResume()
        presenter.onViewActive(this)
        //Hide default main Toolbar and BottomNavigation
        fragmentInteractionListener.apply {
            setToolbarVisible(false)
            setBottomNavigationVisible(false)
        }
    }

    override fun onPause() {
        presenter.onViewInactive()
        super.onPause()
    }

    override fun showImage(source: String) {
        content_iv?.loadUrl(source)
    }

    override fun showTitle(title: String) {
        content_title.text = title
    }

    override fun showDescription(text: String) {
        descDialog.setContent(text)
    }

    private fun initToolbar(){
        val appCompatAct = activity as AppCompatActivity
        appCompatAct.setSupportActionBar(content_toolbar)
        appCompatAct.supportActionBar?.apply {
            setHasOptionsMenu(false)
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
            content_toolbar.bringToFront()
        }
    }

    private fun initRecyclerView() {
        //Setup the RecyclerView
        content_rv?.apply {
            adapter = gridAdapter
            layoutManager = GridLayoutManager(activity, 2)
            setHasFixedSize(true) // Size of data won't change
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //Back to the previous fragment
        if (item.itemId == android.R.id.home){
            fragmentManager?.popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }

    //Update RecyclerView's adapter on a received data
    override fun showContent(contents: ArrayList<Content>) {
        contentsList.clear()
        contentsList.addAll(contents)
        gridAdapter.notifyDataSetChanged()
    }

    //go to a detailed activity
    private fun showDetailsFragment(name: String?) {
        val bundle = Bundle()
        //Remove polish chars
        val ref = "$contentName/Issues/$name/descriptions".normalize()
        bundle.putString(DataType.BUNDLE_CONTENT, ref)
        fragmentInteractionListener.showFragment(
                DetailsFragment::class.java,
                bundle,
                true, FragmentType.TAG_DETAILS)
    }

    //Get RecyclerView's data
    private fun getContent() {
        context?.applicationContext?.let { presenter.getContent(contentName) }
    }

    //Get data like: title, image, description
    private fun getContentInfo() {
        context?.applicationContext?.let { presenter.getContentInfo(contentName) }
    }
}