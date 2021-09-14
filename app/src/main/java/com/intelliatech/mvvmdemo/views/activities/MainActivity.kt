package com.intelliatech.mvvmdemo.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.intelliatech.mvvmdemo.R

class MainActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var navHostFragment: NavHostFragment
    lateinit var navController: NavController
    lateinit var appBarConfig: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController   = navHostFragment.navController
//        toolbar = findViewById(R.id.toolbar)
//        appBarConfig = AppBarConfiguration(navController.graph)
//        toolbar.setupWithNavController(navController,appBarConfig)

    }
}