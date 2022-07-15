package com.ayizor.afeme

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.ayizor.afeme.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        inits()
    }

    private fun inits() {


    }

//    private fun setupNavigation() {
//        val navHostFragment = supportFragmentManager
//            .findFragmentById(binding.navHostFragment.id) as NavHostFragment
//        val navController = navHostFragment.navController
//
//        binding.bottomNavView.setupWithNavController(navController)
//    }
}