package com.czterysery.MVPWithFirebase.ui.details

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.czterysery.MVPWithFirebase.R
import com.czterysery.MVPWithFirebase.data.models.Detail
import com.czterysery.MVPWithFirebase.inflate
import kotlinx.android.synthetic.main.card_details.view.*

/*
    DetailsRecyclerAdapter is responsible for showing cards with content.
 */
class DetailsRecyclerAdapter(private val details: ArrayList<Detail>) :
        RecyclerView.Adapter<DetailsRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(parent.inflate(R.layout.card_details))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(details[position])
    }

    override fun getItemCount(): Int = details.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Detail) = with(itemView) {
            item.name?.let { detail_name.text = it } //Set title
            item.description?.let { expand_text_view.text = it } //Set description
        }
    }

}