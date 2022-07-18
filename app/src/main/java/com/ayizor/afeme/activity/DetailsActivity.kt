package com.ayizor.afeme.activity

import android.os.Bundle
import com.ayizor.afeme.databinding.ActivityDetailsBinding

class DetailsActivity : BaseActivity() {


    lateinit var binding: ActivityDetailsBinding
    val TAG: String = DetailsActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inits()
    }

    private fun inits() {

    }
}