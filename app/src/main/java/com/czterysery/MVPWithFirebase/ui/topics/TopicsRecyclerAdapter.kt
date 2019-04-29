package com.czterysery.MVPWithFirebase.ui.topics

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.czterysery.MVPWithFirebase.R
import com.czterysery.MVPWithFirebase.data.models.Topic
import com.czterysery.MVPWithFirebase.inflate
import com.czterysery.MVPWithFirebase.loadUrl
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_topic.view.*

/*
    Shows list of cards in the TopicsFragment.
    This class returns callback listener that
    handles click action on the card.
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
            item.image?.let { topic_image.loadUrl(it) } //From extension function
            setOnClickListener { listener(item) }
        }
    }
}