package com.czterysery.MVPWithFirebase.ui.topics

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.czterysery.MVPWithFirebase.R
import com.czterysery.MVPWithFirebase.data.models.Topic
import com.czterysery.MVPWithFirebase.inflate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_topic.view.*

/**
 * Created by tmax0 on 20.12.2017.
 */
class TopicsRecyclerAdapter(private val topics: ArrayList<Topic>, private val listener: (Topic) -> Unit):
        RecyclerView.Adapter<TopicsRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(parent.inflate(R.layout.card_topic))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
            holder.bind(topics[position], listener)

    override fun getItemCount(): Int = topics.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(item: Topic, listener: (Topic) -> Unit) = with(itemView) {
            item.name?.let { topic_name.text = it }
            item.image?.let { Picasso.get().load(it).fit().into(topic_image) }
            item.count?.let { topic_read_articles.setSegmentCount(it.toInt()) }
            item.count?.let { topic_read_articles.setCompletedSegments(1) } //Here change clicked articles
            setOnClickListener { listener(item) }
        }
    }
}