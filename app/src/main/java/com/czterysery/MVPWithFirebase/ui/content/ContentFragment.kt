package com.czterysery.MVPWithFirebase.ui.content

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.brouding.simpledialog.SimpleDialog
import com.czterysery.MVPWithFirebase.Constants
import com.czterysery.MVPWithFirebase.R
import com.czterysery.MVPWithFirebase.data.DataRepository
import com.czterysery.MVPWithFirebase.data.local.LocalDataSource
import com.czterysery.MVPWithFirebase.data.models.Content
import com.czterysery.MVPWithFirebase.data.remote.RemoteDataSource
import com.czterysery.MVPWithFirebase.inflate
import com.czterysery.MVPWithFirebase.ui.details.DetailsFragment
import com.czterysery.MVPWithFirebase.util.BaseFragmentInteractionListener
import com.czterysery.MVPWithFirebase.util.NetworkHelper
import com.czterysery.MVPWithFirebase.util.UnicodeFilter
import com.czterysery.MVPWithFirebase.util.mvp.BaseView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_content.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by tmax0 on 31.12.2017.
 */
class ContentFragment: BaseView(), ContentContract.View {
    private val TAG = javaClass.simpleName
    private lateinit var fragmentInteractionListener: BaseFragmentInteractionListener
    private lateinit var presenter: ContentPresenter
    private val dataRepository = DataRepository(
            RemoteDataSource(), LocalDataSource(), NetworkHelper())
    private val contentsList = ArrayList<Content>()

    private val gridAdapter by lazy {
        ContentRecyclerAdapter(contentsList){
            showDetailsFragment(it.name)
        }
    }

    private val descDialog by lazy {
        SimpleDialog.Builder(this.context!!)
                .setTitle(resources.getString(R.string.description_label))
                .setCancelable(true)
                .setBtnConfirmText("OK")
                .setBtnConfirmTextColor(R.color.colorAccent)
                .onConfirm { dialog, _ ->
                    dialog.dismiss()
                }
    }

    private val contentName by lazy {
        arguments?.get(Constants.BUNDLE_CONTENT).toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = ContentPresenter(this, dataRepository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container!!.inflate(R.layout.fragment_content)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeToolbar()

        /*
        Consider add a parallax effect with this library programatically
        https://github.com/kanytu/android-parallax-recyclerview
        ! parallax effect in xml doesn't work !
         */
        content_rv?.apply {
            adapter = gridAdapter
            layoutManager = GridLayoutManager(activity, 2)
            setHasFixedSize(true)
        }

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
        Log.d(TAG, "Fragment detached.")
    }

    override fun onResume() {
        super.onResume()
        presenter.onViewActive(this)
        fragmentInteractionListener.apply {
            setToolbar(false)
            setBottomNavigation(false)
        }
    }

    override fun onPause() {
        presenter.onViewInactive()
        super.onPause()
    }

    override fun showImage(source: String) {
        if (content_iv != null) {
            Picasso.get()
                    .load(source)
                    .fit()
                    .into(content_iv)
        }
    }

    override fun showTitle(title: String) {
        if (content_title != null) {
            content_title.text = title
        }
    }

    override fun showDescription(text: String) {
        if (descDialog != null) {
            //Dialog here
            descDialog.setContent(text)
        }
    }

    private fun initializeToolbar(){
        val appCompatAct = activity as AppCompatActivity
        appCompatAct.setSupportActionBar(content_toolbar)
        appCompatAct.supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
            content_toolbar.bringToFront()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //Back to the previous fragment
        if (item.itemId == android.R.id.home){
            fragmentManager?.popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showContent(contents: ArrayList<Content>) {
        contentsList.clear()
        contentsList.addAll(contents)
        gridAdapter.notifyDataSetChanged()
    }

    private fun showDetailsFragment(name: String?) {
        val bundle = Bundle()
        val ref = UnicodeFilter(false)
                .filter("$contentName/Issues/$name/descriptions").toString()
        bundle.putString(Constants.BUNDLE_CONTENT, ref)
        fragmentInteractionListener.showFragment(
                DetailsFragment::class.java,
                bundle,
                true)
    }

    private fun getContent() {
        context?.applicationContext?.let { presenter.getContent(it, contentName) }
    }

    private fun getContentInfo() {
        context?.applicationContext?.let { presenter.getContentInfo(it, contentName) }
    }
}