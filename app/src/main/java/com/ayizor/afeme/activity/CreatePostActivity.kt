package com.ayizor.afeme.activity


import android.os.Bundle
import com.ayizor.afeme.R
import com.ayizor.afeme.databinding.ActivityCreatePostBinding
import com.ayizor.afeme.fragment.creatpost.PostTypeFragment


class CreatePostActivity : BaseActivity() {
    lateinit var binding: ActivityCreatePostBinding
    val TAG: String = CreatePostActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_creat_post, PostTypeFragment())
                .commit()
        }
        inits()
    }

    private fun inits() {
        //  binding.progressBarMainCreatPost.progress = fm.backStackEntryCount

    }




}