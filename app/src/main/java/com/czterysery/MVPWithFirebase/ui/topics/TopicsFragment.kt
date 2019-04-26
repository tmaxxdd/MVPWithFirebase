package com.czterysery.MVPWithFirebase.ui.topics

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.czterysery.MVPWithFirebase.DataType
import com.czterysery.MVPWithFirebase.FragmentType
import com.czterysery.MVPWithFirebase.R
import com.czterysery.MVPWithFirebase.data.DataRepository
import com.czterysery.MVPWithFirebase.data.local.LocalDataSource
import com.czterysery.MVPWithFirebase.data.models.Topic
import com.czterysery.MVPWithFirebase.data.remote.RemoteDataSource
import com.czterysery.MVPWithFirebase.ui.content.ContentFragment
import com.czterysery.MVPWithFirebase.util.*
import com.czterysery.MVPWithFirebase.util.mvp.BaseView
import kotlinx.android.synthetic.main.fragment_topics.*

/*
    Fragment that shows a list with general topics(RecyclerView)
    according to selected tab (BottomNavigation).

 */
class TopicsFragment: BaseView(), TopicsContract.View {
    private val TAG = javaClass.simpleName
    private val topics = ArrayList<Topic>()
    private val dataRepository = DataRepository(
            RemoteDataSource(),
            LocalDataSource(GsonUtil(SharedPrefsHelper(activity!!.applicationContext))),
            NetworkHelper(activity.applicationContext))
    private lateinit var presenter: TopicsPresenter
    private lateinit var fragmentInteractionListener: BaseFragmentInteractionListener

    private val recyclerAdapter by lazy {
        TopicsRecyclerAdapter(topics){
            //On card clicked
            if(it.name != null)
                //Callback comes from TopicsRecyclerAdapter
                showContentFragment(it.name)
        }
    }

    private val topicsRef by lazy {
        arguments?.get(DataType.BUNDLE_TOPIC).toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = TopicsPresenter(this, dataRepository)
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
        presenter.onViewActive(this)
        fragmentInteractionListener.apply {
            setToolbarVisible(true)
            setBottomNavigationVisible(true)
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

    private fun getTopics(ref: String) {
        context?.applicationContext?.let {
            Log.d(TAG, "Display $TAG with a data = $ref")
            presenter.getTopics(it, ref)
        }
    }

    override fun setProgressBar(show: Boolean) {
        super.setProgressBar(show)
        if (show)
            progress_bar.visibility = View.VISIBLE
        else
            progress_bar.visibility = View.GONE
    }

    private fun showContentFragment(topicName: String) {
        val bundle = Bundle()
        var ref = "$topicsRef/$topicName"
        ref = UnicodeFilter(false).filter(ref).toString()
        bundle.putString(DataType.BUNDLE_CONTENT, ref)
        fragmentInteractionListener.showFragment(
                ContentFragment::class.java,
                bundle,
                true, FragmentType.TAG_CONTENT)
    }
}
