package com.example.firebasetest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Filter
import android.widget.TextView
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.core.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    companion object{
        var TAG = MainActivity::class.java.simpleName
    }
    lateinit var  etName: EditText
    lateinit var  mResults: TextView
    lateinit var  users: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mResults = findViewById(R.id.results);
        etName = findViewById(R.id.eName)
    }

    fun fireStoreHandler(view: View) {
        when(view.id){
            R.id.button_submit ->{
                sendDataFireStore()
            }
            R.id.button_retrieve ->{
                getDataFireStore()
            }
        }
    }

    private fun getDataFireStore() {
        val db = Firebase.firestore
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    mResults.text = "${document.data.getValue("Name")}"
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

    }

    private fun sendDataFireStore() {
        val db = Firebase.firestore
        val name = etName.text.toString()
        val user = hashMapOf(
            "Name" to name,
        )
        Log.i(TAG, "user is" + user)
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }

    }

    private fun onFilter(filter: Filter){
        val db = Firebase.firestore
        var query: CollectionReference = db.collection("users")


    }
}