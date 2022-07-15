package com.ayizor.afeme.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ayizor.afeme.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {

    lateinit var binding: FragmentSearchBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        inits()
        return binding.root
    }

    private fun inits() {
        //setup navigation component settings
////        val navHostFragment =  activity?.supportFragmentManager
////            ?.findFragmentById(binding.navHostFragment.id) as NavHostFragment
////        val navController = navHostFragment.navController
//
//        binding.swichFragments.setOnClickListener {
//            if (navController.currentDestination!!.id == R.id.nav_map) {
//                findNavController().navigate(R.id.action_nav_map_to_nav_list)
//            } else {
//                findNavController().navigate(R.id.action_nav_list_to_nav_map)
//            }
//        }
    }


}