package com.czterysery.MVPWithFirebase.ui.details

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.czterysery.MVPWithFirebase.DataType
import com.czterysery.MVPWithFirebase.R
import com.czterysery.MVPWithFirebase.data.DataRepository
import com.czterysery.MVPWithFirebase.data.local.LocalDataSource
import com.czterysery.MVPWithFirebase.data.models.Detail
import com.czterysery.MVPWithFirebase.data.remote.RemoteDataSource
import com.czterysery.MVPWithFirebase.inflate
import com.czterysery.MVPWithFirebase.util.BaseFragmentInteractionListener
import com.czterysery.MVPWithFirebase.util.GsonUtil
import com.czterysery.MVPWithFirebase.util.NetworkHelper
import com.czterysery.MVPWithFirebase.util.SharedPrefsHelper
import com.czterysery.MVPWithFirebase.util.mvp.BaseFragment
import kotlinx.android.synthetic.main.fragment_details.*
import org.jetbrains.anko.support.v4.toast

/**
 * Created by tmax0 on 07.01.2018.
 */
//TODO Refactor
class DetailsFragment: BaseFragment(), DetailsContract.Fragment {
    private val TAG = javaClass.simpleName
    private val details = ArrayList<Detail>()
    private val dataRepository = DataRepository(
            RemoteDataSource(),
            LocalDataSource(GsonUtil(SharedPrefsHelper(activity!!.applicationContext))),
            NetworkHelper(activity!!.applicationContext))

    private lateinit var presenter: DetailsPresenter
    private lateinit var fragmentInteractionListener: BaseFragmentInteractionListener

    private val detailsAdapter by lazy {
        DetailsRecyclerAdapter(context, details) {
            toast("Clicked on card!")
        }
    }

    private val contentName by lazy {
        arguments?.get(DataType.BUNDLE_CONTENT).toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = DetailsPresenter(this, dataRepository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container!!.inflate(R.layout.fragment_details)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeToolbar()

        details_rv.apply {
            adapter = detailsAdapter
            layoutManager = LinearLayoutManager(context)
            isNestedScrollingEnabled = false
        }

        getDetails()
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
    }

    override fun onPause() {
        super.onPause()
        presenter.onViewInactive()
    }

    private fun initializeToolbar(){
        val appCompatAct = activity as AppCompatActivity
        appCompatAct.setSupportActionBar(details_toolbar)
        appCompatAct.supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp)
            details_toolbar.bringToFront()
            details_shadow.bringToFront()
        }
        details_title.text = getOnlyDetailsBranch(contentName)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //Back to the previous fragment
        if (item.itemId == android.R.id.home){
            fragmentManager?.popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showDetails(cards: ArrayList<Detail>) {
        details.clear()
        details.addAll(cards)
        detailsAdapter.notifyDataSetChanged()
    }

    override fun shouldShowPlaceholderText() {

    }

    private fun getDetails() {
        context?.applicationContext?.let { presenter.getDetails(it, contentName) }
    }

    private fun getOnlyDetailsBranch(text: String): String {
        val bits = text.split("/")
        return bits[bits.size - 2] //Return the second word from the end
    }
}