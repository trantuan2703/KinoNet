package com.example.kinonet.code.View.Authen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.kinonet.R
import com.example.kinonet.code.View.Main.ContainerActivity
import java.util.*
import kotlin.concurrent.schedule

class SplashActivity : AppCompatActivity() {
    private var imvSplash : ImageView ?=null
    private val preference by lazy { getSharedPreferences("main", Context.MODE_PRIVATE) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        imvSplash=findViewById(R.id.imv_splash)
        imvSplash?.let { Glide.with(this).load(R.raw.splash).into(it) }
    }

    override fun onResume() {
        super.onResume()
        Timer().schedule(4000) {
            checkAccount()
        }

    }

    private fun checkAccount() {
         val isRemembered = preference.getBoolean("RememberMe",false)
            if (isRemembered){
                startActivity(Intent(this,ContainerActivity::class.java))
            }else{
                startActivity(Intent(this,LoginActivity::class.java))
            }
    }
}