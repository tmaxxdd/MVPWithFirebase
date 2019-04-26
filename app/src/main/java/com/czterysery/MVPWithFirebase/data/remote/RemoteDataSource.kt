package com.czterysery.MVPWithFirebase.data.remote

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

/*
    This class retrieve data from the Firebase Realtime Database.
    More about creating your own database and implementation here:
    https://firebase.google.com/docs/database/android/start
 */
class RemoteDataSource: DataSource() {
    private val TAG = javaClass.simpleName

    override fun getTopics(ref: String, callback: GetTopicsCallback) {
        //Get path to data according to selected tab
        val databaseRef = FirebaseDatabase.getInstance().getReference(ref)
        val topics = ArrayList<Topic>()
        Log.d(TAG, "Retrieving topics from the $ref")
        databaseRef.orderByChild("index").addValueEventListener(object : ValueEventListener {

            override fun onDataChange(data: DataSnapshot) {
                /*
                    In the database a child contains more fields
                    than Topic model. So we have to choose which one we want to match.
                 */
                data.children.forEach {
                    val item = Topic(
                            it.child("name").value?.toString(),
                            it.child("image").value?.toString(),
                            it.child("count").value?.toString()
                            )
                    topics.add(item)
                }
                callback.onSuccess(topics)
            }

            override fun onCancelled(error: DatabaseError) {
                error.let {
                    callback.onFailure(error.toException().fillInStackTrace())
                }
            }
        })
    }

    override fun getContent(ref: String, callback: GetContentCallback) {
        val databaseRef = FirebaseDatabase.getInstance().getReference(ref).child("Issues")
        val contents = ArrayList<Content>()
        Log.d(TAG, "Retrieving content from the $ref")
        databaseRef.orderByChild("index").addValueEventListener( object : ValueEventListener {

            //Content model represents entity in the database
            override fun onDataChange(data: DataSnapshot) {
                data.children.forEach {
                    contents.add(it.getValue(Content::class.java)!!)
                }
                callback.onSuccess(contents)
            }

            override fun onCancelled(error: DatabaseError) {
                error.let {
                    callback.onFailure(error.toException().fillInStackTrace())
                }
            }
        })
    }

    override fun getContentInfo(ref: String, callback: GetContentInfoCallback) {
        val databaseRef = FirebaseDatabase.getInstance().getReference(ref)
        Log.d(TAG, "Retrieving content from the $ref")
        databaseRef.addValueEventListener( object : ValueEventListener {

            //Get only one object from the database that matches reference
            override fun onDataChange(data: DataSnapshot) {
                data.let {
                    callback.onSuccess(it.getValue(ContentInfo::class.java)!!)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                error.let {
                    callback.onFailure(error.toException().fillInStackTrace())
                }
            }
        })
    }

    override fun getDetails(ref: String, callback: GetDetailsCallback) {
        val databaseRef = FirebaseDatabase.getInstance().getReference(ref)
        val details = ArrayList<Detail>()
        Log.d(TAG, "Retrieving details from the $ref")
        databaseRef.addValueEventListener( object : ValueEventListener {

            override fun onDataChange(data: DataSnapshot) {
                data.children.forEach {
                    details.add(it.getValue(Detail::class.java)!!)
                }
                callback.onSuccess(details)
            }

            override fun onCancelled(error: DatabaseError) {
                error.let {
                    callback.onFailure(error.toException().fillInStackTrace())
                }
            }
        })
    }

}
