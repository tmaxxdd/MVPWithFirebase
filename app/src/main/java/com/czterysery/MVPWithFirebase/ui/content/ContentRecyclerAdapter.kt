package com.czterysery.MVPWithFirebase.ui.content

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.czterysery.MVPWithFirebase.R
import com.czterysery.MVPWithFirebase.data.models.Content
import com.czterysery.MVPWithFirebase.inflate
import com.czterysery.MVPWithFirebase.loadUrl
import kotlinx.android.synthetic.main.card_content.view.*

/**
 * Created by tmax0 on 31.12.2017.
 */
class ContentRecyclerAdapter(val contents: ArrayList<Content>, val listener: (Content) -> Unit):
        RecyclerView.Adapter<ContentRecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(parent.inflate(R.layout.card_content))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
            holder.bind(contents[position], listener)

    override fun getItemCount(): Int = contents.size

    fun clear() {
        contents.clear()
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(item: Content, listener: (Content) -> Unit) = with(itemView) {
            item.name?.let { content_name.text = it }
            item.image?.let { content_image.loadUrl(it) }
            setOnClickListener { listener(item) }
        }
    }
}