package com.example.kinonet.code.View.Authen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.kinonet.R
import com.tuantran.cloudfirestore2.Database.FirestoreContext

class RegisterActivity : AppCompatActivity() {
    private var edtUsername : EditText ?=null
    private var edtPassword : EditText ?=null
    private var edtName : EditText ?=null
    private var tvCreate : TextView ?=null
    private var imvBack : ImageView ?=null
    private lateinit var fsContext: FirestoreContext

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        init()
        registerEvent()
    }

    private fun registerEvent() {
        imvBack!!.setOnClickListener{ finish()}
        tvCreate!!.setOnClickListener {
            val username=edtUsername!!.text.toString()
            val pass = edtPassword!!.text.toString()
            val fullName = edtName!!.text.toString()
            if (username.isNotEmpty() && pass.isNotEmpty() && fullName.isNotEmpty()){
                fsContext.returnPass(username, object : FirestoreContext.FsCallBack {
                    override fun onSuccess(value: String) {
                        if (value != "null") {
                            Log.d(FirestoreContext.Tag, "username existed")
                            Toast.makeText(this@RegisterActivity, "Username existed", Toast.LENGTH_SHORT).show()
                        } else {
                            Log.d(FirestoreContext.Tag, "username doesn't exist")
                            register(username,pass,fullName)
                        }
                    }

                    override fun onFail(message: String) {
                        Log.d(FirestoreContext.Tag, "Fail: $message")
                    }

                })
            }else{
                Toast.makeText(this, "Unsuccessful", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun register(username: String, pass: String, fullName: String) {
        fsContext.registerAccount(username, pass, fullName, object : FirestoreContext.FsCallBack {
            override fun onSuccess(value: String) {
                Log.d(FirestoreContext.Tag,value)
                Toast.makeText(this@RegisterActivity, "Register Successful", Toast.LENGTH_SHORT).show()
                finish()
            }

            override fun onFail(message: String) {
                Log.d(FirestoreContext.Tag, "Fail: $message")
            }
        })
    }

    private fun init(){
        edtUsername=findViewById(R.id.edt_register_username)
        edtPassword=findViewById(R.id.edt_register_password)
        edtName = findViewById(R.id.edt_register_full_name)
        tvCreate = findViewById(R.id.tv_register_create)
        imvBack = findViewById(R.id.imv_register_back)
        fsContext = FirestoreContext.instance!!
    }
}