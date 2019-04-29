package com.czterysery.MVPWithFirebase.ui.topics

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.czterysery.MVPWithFirebase.util.constants.DataType
import com.czterysery.MVPWithFirebase.util.constants.FragmentType
import com.czterysery.MVPWithFirebase.R
import com.czterysery.MVPWithFirebase.data.DataRepository
import com.czterysery.MVPWithFirebase.data.local.LocalDataSource
import com.czterysery.MVPWithFirebase.data.models.Topic
import com.czterysery.MVPWithFirebase.data.remote.RemoteDataSource
import com.czterysery.MVPWithFirebase.normalize
import com.czterysery.MVPWithFirebase.ui.content.ContentFragment
import com.czterysery.MVPWithFirebase.util.helpers.GsonHelper
import com.czterysery.MVPWithFirebase.util.helpers.NetworkHelper
import com.czterysery.MVPWithFirebase.util.helpers.SharedPrefsHelper
import com.czterysery.MVPWithFirebase.util.mvp.BaseFragment
import com.czterysery.MVPWithFirebase.util.mvp.BaseFragmentInteractionListener
import kotlinx.android.synthetic.main.fragment_topics.*

/*
    Fragment which shows a list with general topics(RecyclerView)
    according to selected tab (BottomNavigation).
 */

class TopicsFragment: BaseFragment(), TopicsContract.Fragment {
    private val TAG = javaClass.simpleName
    private val topics = ArrayList<Topic>()
    private lateinit var presenter: TopicsPresenter
    private lateinit var fragmentInteractionListener: BaseFragmentInteractionListener

    private val recyclerAdapter by lazy {
        TopicsRecyclerAdapter(topics){ card ->
            //On card clicked
            card.name?.let { name ->
                //Callback comes from TopicsRecyclerAdapter
                showContentFragment(name)
            }
        }
    }

    private val dataRepository by lazy {
        //First invocation is in the onCreate, so we are sure that activity has been instantiated
        DataRepository(
                RemoteDataSource(),
                LocalDataSource(GsonHelper(SharedPrefsHelper(activity!!.applicationContext))),
                NetworkHelper(activity!!.applicationContext))
    }

    private val topicsRef by lazy {
        arguments?.get(DataType.BUNDLE_TOPIC).toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = TopicsPresenter(dataRepository)
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
        Log.d(TAG, "$TAG detached")
    }

    override fun onResume() {
        super.onResume()
        //Notice presenter that view can be accessible
        presenter.onViewActive(this)
        fragmentInteractionListener.apply {
            setToolbarVisible(true)
            setBottomNavigationVisible(true)
        }
    }

    override fun onPause() {
        //Notice presenter that view cannot be accessible
        presenter.onViewInactive()
        super.onPause()
    }

    override fun showTopics(cards: ArrayList<Topic>) {
        topics.clear()
        topics.addAll(cards)
        recyclerAdapter.notifyDataSetChanged()
    }

    private fun getTopics(ref: String) {
        //Request for a data
        presenter.getTopics(ref)
    }

    //From BaseFragment
    override fun setProgressBar(visible: Boolean) {
        super.setProgressBar(visible)
        if (visible)
            progress_bar.visibility = View.VISIBLE
        else
            progress_bar.visibility = View.GONE
    }

    private fun showContentFragment(topicName: String) {
        val bundle = Bundle()
        //Remove polish chars
        val ref= "$topicsRef/$topicName".normalize()
        Log.d(TAG, "After normalization: $ref")
        bundle.putString(DataType.BUNDLE_CONTENT, ref)
        fragmentInteractionListener.showFragment(
                ContentFragment::class.java,
                bundle,
                true, FragmentType.TAG_CONTENT)
    }
}
