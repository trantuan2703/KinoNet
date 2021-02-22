package com.tuantran.cloudfirestore2.Database

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreContext{
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    companion object {
        const val Tag: String="FsContext"
        var instance: FirestoreContext? = null
            get() {
                if (field == null) {
                    field = FirestoreContext()
                }
                return field
            }
            private set
    }

    fun returnPass(username : String,fsCallBack: FsCallBack){
        db.collection("users")
            .whereEqualTo("username", username)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty){
                    fsCallBack.onSuccess("null")
                }else{
//                    for (document in documents) {
//                        Log.d(Tag, "${document.id} => ${document.data}")
//                    }
                    fsCallBack.onSuccess(documents.first().data["password"].toString())
                }
            }
            .addOnFailureListener { exception ->
                fsCallBack.onFail(exception.message.toString())
            }
    }

    fun registerAccount(username: String,pass: String,fullname: String,fsCallback: FsCallBack){
        val data = hashMapOf(
            "username" to username,
            "password" to pass,
            "fullname" to fullname
        )

        db.collection("users")
            .add(data)
            .addOnSuccessListener { documentReference ->
                fsCallback.onSuccess(documentReference.id)
            }
            .addOnFailureListener { e ->
                fsCallback.onFail(e.message.toString())
            }
    }

    interface FsCallBack {
        fun onSuccess(value: String)
        fun onFail(message: String)
    }
}


