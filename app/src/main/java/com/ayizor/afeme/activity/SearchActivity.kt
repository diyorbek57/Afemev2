package com.ayizor.afeme.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.ayizor.afeme.R
import com.ayizor.afeme.databinding.ActivitySearchBinding

class SearchActivity : BaseActivity() {

    lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        inits()
    }

    private fun inits() {
        setupSearchEditText()
    }


    private fun setupSearchEditText() {
        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isNotEmpty()) {
                    //set background and text color to button

                    binding.ivClear.visibility = View.VISIBLE
                    binding.ivBack.setImageResource(R.drawable.ic_close)

                } else {
                    binding.ivClear.visibility = View.GONE
                    binding.ivBack.setImageResource(R.drawable.ic_back)

                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
    }
}