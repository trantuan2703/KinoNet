package com.tuantran.cloudfirestore2.Database

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
            "fullname" to fullname,
            "avatar" to "https://firebasestorage.googleapis.com/v0/b/fir-kinonet.appspot.com/o/AccountAvatar%2Fmovie.svg?alt=media&token=06096280-3605-4732-8baf-b7213fa3632e"
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


