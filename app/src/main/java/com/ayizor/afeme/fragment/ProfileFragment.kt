package com.ayizor.afeme.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ayizor.afeme.activity.SettingsActivity
import com.ayizor.afeme.api.main.ApiInterface
import com.ayizor.afeme.databinding.FragmentProfileBinding
import com.ayizor.afeme.fragment.searchfragment.ListFragment


class ProfileFragment : Fragment() {


    lateinit var binding: FragmentProfileBinding
    val TAG: String = ListFragment::class.java.simpleName
    var dataService: ApiInterface? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        inits()
        return binding.root
    }

    private fun inits() {
        binding.llSetting.setOnClickListener {
            val intent = Intent(requireContext(), SettingsActivity::class.java)
            startActivity(intent)
        }
    }

}