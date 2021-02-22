package com.example.kinonet.code.View.Authen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.kinonet.R
import com.example.kinonet.code.View.Main.HomeActivity
import com.tuantran.cloudfirestore2.Database.FirestoreContext

class LoginActivity : AppCompatActivity(){
    private var edtUsername :EditText?=null
    private var edtPassword : EditText?=null
    private var btnLogin : Button?=null
    private var rbRememberLogin : RadioButton?=null
    private var rememberLogin = false
    private lateinit var tvMoveToRegister: TextView
    private lateinit var fsContext: FirestoreContext
    private val preference by lazy { getSharedPreferences("main", Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
        registering()
    }

    private fun init() {
        edtUsername = findViewById(R.id.edt_username)
        edtPassword = findViewById(R.id.edt_password)
        btnLogin = findViewById(R.id.btn_login)
        rbRememberLogin = findViewById(R.id.rb_remember_login)
        tvMoveToRegister = findViewById(R.id.tv_Login_To_Register)
        fsContext = FirestoreContext.instance!!
    }

    private fun registering() {
        btnLogin!!.setOnClickListener {
            val username = edtUsername!!.text.toString()
            val password = edtPassword!!.text.toString()
            if ( username.isNotEmpty() && password.isNotEmpty()){
                fsContext.returnPass(username,object :FirestoreContext.FsCallBack{
                    override fun onSuccess(value: String) {
                        Log.d(FirestoreContext.Tag,value)
                        login(value,password)
                    }

                    override fun onFail(message: String) {
                        Log.d(FirestoreContext.Tag, "Fail: $message")
                    }
                })
            }else{
                Toast.makeText(this, "Unsuccessful", Toast.LENGTH_SHORT).show()
            }
        }
        rbRememberLogin!!.setOnClickListener{
            if (rbRememberLogin!!.isChecked) {
                val editor=preference.edit()
                if (!rememberLogin) {
                    rbRememberLogin!!.isChecked = true
                    rememberLogin = true
                    editor.putBoolean("RememberMe", true)
                } else {
                    rememberLogin = false
                    rbRememberLogin!!.isChecked = false
                    editor.putBoolean("RememberMe", false)
                }
                editor.apply()
            }
        }
        tvMoveToRegister.setOnClickListener{
            startActivity(Intent(this,RegisterActivity::class.java))
        }
    }

    private fun login(value: String, password: String) {
        if (value=="null")
            Toast.makeText(this, "Username doesn't exist", Toast.LENGTH_SHORT).show()
        if (value!=password){
            Toast.makeText(this, "Password isn't correct", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,HomeActivity::class.java))
        }
    }

}

