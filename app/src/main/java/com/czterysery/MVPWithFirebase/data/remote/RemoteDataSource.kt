package com.czterysery.MVPWithFirebase.data.remote

import android.content.Context
import android.util.Log
import com.czterysery.MVPWithFirebase.data.DataSource
import com.czterysery.MVPWithFirebase.data.models.Content
import com.czterysery.MVPWithFirebase.data.models.ContentInfo
import com.czterysery.MVPWithFirebase.data.models.Detail
import com.czterysery.MVPWithFirebase.data.models.Topic
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
 * Created by tmax0 on 25.12.2017.
 */
class RemoteDataSource: DataSource() {
    private val TAG = javaClass.simpleName

    override fun getTopics(context: Context, ref: String, callback: GetTopicsCallback) {
        val topics = ArrayList<Topic>()
        Log.d(TAG, "Retrieving content from the $ref")
        val databaseRef = FirebaseDatabase.getInstance().getReference(ref)
        databaseRef.orderByChild("index").addValueEventListener(object : ValueEventListener {

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach {
                    val item = Topic(
                            it.child("name").value?.toString(),
                            it.child("image").value?.toString(),
                            it.child("count").value?.toString()
                            )
                    topics.add(item)
                }
                callback.onSuccess(topics)
            }

            override fun onCancelled(p0: DatabaseError) {
                p0.let {
                    callback.onFailure(p0.toException().fillInStackTrace())
                }
            }
        })
    }

    override fun getContent(context: Context, ref: String, callback: GetContentCallback) {
        val contents = ArrayList<Content>()
        Log.d(TAG, "Retrieving content from the $ref.")
        val databaseRef = FirebaseDatabase.getInstance().getReference(ref).child("Issues")
        databaseRef.orderByChild("index").addValueEventListener( object : ValueEventListener {

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach {
                    contents.add(it.getValue(Content::class.java)!!)
                }
                callback.onSuccess(contents)
            }

            override fun onCancelled(p0: DatabaseError) {
                p0.let {
                    callback.onFailure(p0.toException().fillInStackTrace())
                }
            }
        })
    }

    override fun getContentInfo(context: Context, ref: String, callback: GetContentInfoCallback) {
        Log.d(TAG, "Retrieving content from the $ref")
        val databaseRef = FirebaseDatabase.getInstance().getReference(ref)
        databaseRef.addValueEventListener( object : ValueEventListener {

            override fun onDataChange(p0: DataSnapshot) {
                p0.let {
                    callback.onSuccess(it.getValue(ContentInfo::class.java)!!)
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                p0.let {
                    callback.onFailure(p0.toException().fillInStackTrace())
                }
            }
        })
    }

    override fun getDetails(context: Context, ref: String, callback: GetDetailsCallback) {
        val details = ArrayList<Detail>()
        val databaseRef = FirebaseDatabase.getInstance().getReference(ref)
        Log.d(TAG, "Retrieving content from the $ref")
        databaseRef.addValueEventListener( object : ValueEventListener {

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach {
                    details.add(it.getValue(Detail::class.java)!!)
                }
                Log.d(TAG, "Details list: $details")
                callback.onSuccess(details)
            }

            override fun onCancelled(p0: DatabaseError) {
                p0.let {
                    callback.onFailure(p0.toException().fillInStackTrace())
                }
            }
        })
    }

}
