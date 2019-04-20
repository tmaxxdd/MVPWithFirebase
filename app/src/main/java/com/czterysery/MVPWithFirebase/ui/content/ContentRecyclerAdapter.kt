package com.czterysery.MVPWithFirebase.ui.content

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.czterysery.MVPWithFirebase.R
import com.czterysery.MVPWithFirebase.data.models.Content
import com.czterysery.MVPWithFirebase.inflate
import com.czterysery.MVPWithFirebase.loadUrl
import kotlinx.android.synthetic.main.card_content.view.*

/*
    Links data with view in a GridView in the ContentFragment.
    In a constructor is passed lambda listener that allows to handle
    click actions in the ContentFragment. More about it here:
    https://antonioleiva.com/recyclerview-adapter-kotlin
 */
class ContentRecyclerAdapter(private val contents: ArrayList<Content>, private val listener: (Content) -> Unit):
        RecyclerView.Adapter<ContentRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(parent.inflate(R.layout.card_content))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
            holder.bind(contents[position], listener)

    override fun getItemCount(): Int = contents.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(item: Content, listener: (Content) -> Unit) = with(itemView) {
            item.name?.let { content_name.text = it }
            item.image?.let { content_image.loadUrl(it) }
            setOnClickListener { listener(item) }
        }
    }
}