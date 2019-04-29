package com.czterysery.MVPWithFirebase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso

/*
 * Extension function that wraps inflater for any view
 * like ViewHolder, Fragment etc.
 */

fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater
            .from(context)
            .inflate(layoutRes, this, false)
}

/*
 * Default extension function which uses Picasso to load images
 * on ImageViews itself.
 */
fun ImageView.loadUrl(url: String) {
    return Picasso.get().load(url).into(this)
}

//Replace all polish diacritics
fun String.normalize(): String {
    val original = arrayOf("Ą", "ą", "Ć", "ć", "Ę", "ę", "Ł", "ł", "Ń", "ń", "Ó", "ó", "Ś", "ś", "Ź", "ź", "Ż", "ż")
    val normalized = arrayOf("A", "a", "C", "c", "E", "e", "L", "l", "N", "n", "O", "o", "S", "s", "Z", "z", "Z", "z")

    return this.map { char ->
        val index = original.indexOf(char.toString())
        if (index >= 0) normalized[index] else char
    }.joinToString("")
}

