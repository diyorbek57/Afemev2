package com.ayizor.afemev2

import android.os.Bundle
import com.ayizor.afemev2.activity.BaseActivity
import com.ayizor.afemev2.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    lateinit var binding: ActivityMainBinding
    val TAG: String = MainActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inits()
    }

    private fun inits() {

    }
}