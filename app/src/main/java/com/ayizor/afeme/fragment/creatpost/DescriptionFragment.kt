package com.ayizor.afeme.fragment.creatpost

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.ayizor.afeme.R
import com.ayizor.afeme.activity.PreviewCreatedPostActivity
import com.ayizor.afeme.databinding.FragmentDescriptionBinding
import com.ayizor.afeme.manager.PostPrefsManager


class DescriptionFragment : Fragment() {


    lateinit var binding: FragmentDescriptionBinding
    val TAG: String = PriceFragment::class.java.simpleName
    var fragmentNumber = 9
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDescriptionBinding.inflate(inflater, container, false)
        inits()
        return binding.root
    }

    private fun inits() {
        activity?.findViewById<ProgressBar>(R.id.progress_bar_main_creat_post)?.progress =
            fragmentNumber
        binding.etDescription.isFocusableInTouchMode = true;
        binding.etDescription.requestFocus();

        binding.btnNext.setOnClickListener {
            PostPrefsManager(requireContext()).storeDescription(binding.etDescription.text.toString())
            val intent = Intent(requireContext(), PreviewCreatedPostActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }


}