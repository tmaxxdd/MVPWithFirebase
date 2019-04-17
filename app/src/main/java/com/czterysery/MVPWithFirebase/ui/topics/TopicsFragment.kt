package com.czterysery.MVPWithFirebase.ui.topics

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.czterysery.MVPWithFirebase.Constants
import com.czterysery.MVPWithFirebase.R
import com.czterysery.MVPWithFirebase.data.DataRepository
import com.czterysery.MVPWithFirebase.data.local.LocalDataSource
import com.czterysery.MVPWithFirebase.data.models.Topic
import com.czterysery.MVPWithFirebase.data.remote.RemoteDataSource
import com.czterysery.MVPWithFirebase.ui.content.ContentFragment
import com.czterysery.MVPWithFirebase.util.BaseFragmentInteractionListener
import com.czterysery.MVPWithFirebase.util.NetworkHelper
import com.czterysery.MVPWithFirebase.util.UnicodeFilter
import com.czterysery.MVPWithFirebase.util.mvp.BaseView
import kotlinx.android.synthetic.main.fragment_topics.*

/**
 * Created by tmax0 on 22.12.2017.
 */
class TopicsFragment: BaseView(), TopicsContract.View {
    private val TAG = javaClass.simpleName
    private val topics = ArrayList<Topic>()
    private val dataRepository = DataRepository(
            RemoteDataSource(), LocalDataSource(), NetworkHelper())
    private lateinit var presenter: TopicsPresenter
    private lateinit var fragmentInteractionListener: BaseFragmentInteractionListener

    private val recyclerAdapter by lazy {
        TopicsRecyclerAdapter(topics){
            //On card clicked
            if(it.name != null)
                showContentFragment(it.name)
        }
    }

    private val topicsRef by lazy {
        arguments?.get(Constants.BUNDLE_TOPIC).toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = TopicsPresenter(this, dataRepository)
        retainInstance = true

        Log.d(TAG, "Bundle in topicFragment: $topicsRef")

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_topics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        topics_rv.apply {
            adapter = recyclerAdapter
            layoutManager = LinearLayoutManager(context)
            isNestedScrollingEnabled = false
        }

        getTopics(topicsRef)
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
            setToolbar(true)
            setBottomNavigation(true)
        }
    }

    override fun onPause() {
        presenter.onViewInactive()
        super.onPause()
    }

    override fun showTopics(cards: ArrayList<Topic>) {
        topics.clear()
        topics.addAll(cards)
        recyclerAdapter.notifyDataSetChanged()
    }

    override fun shouldShowPlaceholderText() {
        if (topics.isEmpty()){
            textView.visibility = View.VISIBLE
        }else{
            textView.visibility = View.GONE
        }
    }

    private fun getTopics(ref: String) {
        context?.applicationContext?.let { presenter.getTopics(it, ref) }
    }

    private fun showContentFragment(topicName: String) {
        val bundle = Bundle()
        var ref = "$topicsRef/$topicName"
        ref = UnicodeFilter(false).filter(ref).toString()
        bundle.putString(Constants.BUNDLE_CONTENT, ref)
        fragmentInteractionListener.showFragment(
                ContentFragment::class.java,
                bundle,
                true)
    }
}
