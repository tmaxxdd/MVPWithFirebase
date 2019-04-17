package com.czterysery.MVPWithFirebase.ui.details

import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.czterysery.MVPWithFirebase.R
import com.czterysery.MVPWithFirebase.data.models.Detail
import com.czterysery.MVPWithFirebase.inflate
import kotlinx.android.synthetic.main.card_details.view.*

/**
 * Created by tmax0 on 08.01.2018.
 */
class DetailsRecyclerAdapter(val context: Context?,
                             private val details: ArrayList<Detail>,
                             private val listener: (Detail) -> (Unit)) :
        RecyclerView.Adapter<DetailsRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(parent.inflate(R.layout.card_details))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val type = Typeface.createFromAsset(context?.assets,
                "fonts/Lato-Italic.ttf")
        holder.bind(details[position], type, listener)
    }

    override fun getItemCount(): Int = details.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Detail, typeface: Typeface, listener: (Detail) -> Unit) = with(itemView) {
            item.name?.let { detail_name.text = it }
            item.description?.let {
                expand_text_view.apply {
                    text = it
                    expandable_text.typeface = typeface
                }
                setOnClickListener { listener(item) }
            }
        }
    }

}