package com.example.kinonet.code.View.Main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.kinonet.R
import com.example.kinonet.code.Adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

class ContainerActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPagerAdapter : ViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)
        init()
    }

    @SuppressLint("NewApi")
    private fun init() {
        viewPager = findViewById(R.id.viewpager)
        tabLayout = findViewById(R.id.tl_tabLayout)
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_home))
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_theater))
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_user))
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = viewPagerAdapter
        viewPager.isUserInputEnabled = false
        tabLayout.getTabAt(0)?.icon?.setTint(resources.getColor(R.color.app_color1, theme))
        tabLayout.getTabAt(1)?.icon?.setTint(resources.getColor(R.color.gray_400, theme))
        tabLayout.getTabAt(2)?.icon?.setTint(resources.getColor(R.color.gray_400, theme))
        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            @SuppressLint("NewApi")
            override fun onTabSelected(tab: TabLayout.Tab) {
                tab.icon?.setTint(resources.getColor(R.color.app_color1, theme))
            }

            @SuppressLint("NewApi")
            override fun onTabUnselected(tab: TabLayout.Tab) {
                tab.icon?.setTint(resources.getColor(R.color.gray_400, theme))
            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }
}