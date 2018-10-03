package com.arttttt.smokekerneltweaks.ui.activities

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import com.arttttt.smokekerneltweaks.R
import com.arttttt.smokekerneltweaks.ui.fragments.FragmentDevice
import com.arttttt.smokekerneltweaks.ui.fragments.FragmentDisplay
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initNavigationView()

        if (savedInstanceState == null)
            initFirstFragment()
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun initFirstFragment() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, FragmentDevice.getInstance())
                .commit()

        val item = nav_view.menu.getItem(0)
        item.isChecked = true
        title = item.title
    }

    private fun initNavigationView() {
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener { item ->

            var fragment: Fragment? = null

            when (item.itemId) {
                R.id.nav_device -> {
                    fragment = FragmentDevice.getInstance()
                }
                R.id.nav_cpu -> {
                    fragment = FragmentDisplay.getInstance()
                }
            }

            if (fragment != null) {
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, fragment)
                        .commit()
            }

            title = item.title

            drawer.closeDrawer(GravityCompat.START)

            true
        }
    }
}
