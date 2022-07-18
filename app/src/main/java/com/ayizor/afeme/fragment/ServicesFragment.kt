package com.ayizor.afeme.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ayizor.afeme.databinding.FragmentServicesBinding


class ServicesFragment : Fragment() {
    lateinit var binding: FragmentServicesBinding
    val TAG: String = ServicesFragment::class.java.simpleName


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentServicesBinding.inflate(inflater, container, false)

        inits()
        return binding.root
    }

    private fun inits() {


    }


}