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
import com.czterysery.MVPWithFirebase.util.constants.DataType
import com.czterysery.MVPWithFirebase.R
import com.czterysery.MVPWithFirebase.data.DataRepository
import com.czterysery.MVPWithFirebase.data.local.LocalDataSource
import com.czterysery.MVPWithFirebase.data.models.Detail
import com.czterysery.MVPWithFirebase.data.remote.RemoteDataSource
import com.czterysery.MVPWithFirebase.inflate
import com.czterysery.MVPWithFirebase.util.mvp.BaseFragmentInteractionListener
import com.czterysery.MVPWithFirebase.util.helpers.GsonHelper
import com.czterysery.MVPWithFirebase.util.helpers.NetworkHelper
import com.czterysery.MVPWithFirebase.util.helpers.SharedPrefsHelper
import com.czterysery.MVPWithFirebase.util.mvp.BaseFragment
import kotlinx.android.synthetic.main.fragment_details.*

/*
    DetailsFragment shows title of selected issue
    and list of cards with content.
 */
class DetailsFragment: BaseFragment(), DetailsContract.Fragment {
    private val TAG = javaClass.simpleName
    private val details = ArrayList<Detail>()
    private lateinit var presenter: DetailsPresenter
    private lateinit var fragmentInteractionListener: BaseFragmentInteractionListener

    private val dataRepository by lazy {
        //First invocation is in the onCreate, so we are sure that activity has been instantiated
        DataRepository(
                RemoteDataSource(),
                LocalDataSource(GsonHelper(SharedPrefsHelper(activity!!.applicationContext))),
                NetworkHelper(activity!!.applicationContext))
    }

    private val detailsAdapter by lazy {
        DetailsRecyclerAdapter(details)
    }

    private val contentName by lazy {
        arguments?.get(DataType.BUNDLE_CONTENT).toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = DetailsPresenter(dataRepository)
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
        Log.i(TAG, "$TAG detached.")
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

    private fun getDetails() {
        context?.applicationContext?.let { presenter.getDetails(contentName) }
    }

    private fun getOnlyDetailsBranch(text: String): String {
        val bits = text.split("/")
        return bits[bits.size - 2] //Return the second word from the end
    }
}