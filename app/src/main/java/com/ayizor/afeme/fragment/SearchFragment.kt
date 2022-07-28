package com.ayizor.afeme.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.ayizor.afeme.R
import com.ayizor.afeme.activity.FilterActivity
import com.ayizor.afeme.databinding.FragmentSearchBinding
import com.ayizor.afeme.fragment.searchfragment.ListFragment
import com.ayizor.afeme.fragment.searchfragment.MapFragment
import com.ayizor.afeme.manager.PrefsManager


class SearchFragment : Fragment() {

    lateinit var binding: FragmentSearchBinding
    val TAG: String = SearchFragment::class.java.simpleName


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        Log.e(TAG, "onCreateView")



        if (!PrefsManager(requireContext()).loadSearchFragment().isNullOrEmpty()) {
            if (PrefsManager(requireContext()).loadSearchFragment().contentEquals("ListFragment")) {
                Log.e(TAG, " is MapFragment")
                callListFragment()
            } else {
                Log.e(TAG, " not MapFragment")
                callMapFragment()
            }
        } else {
            callMapFragment()
        }





        inits()
        return binding.root
    }

    private fun inits() {

        binding.tvFilter.setOnClickListener {
            val intent = Intent(requireContext(), FilterActivity::class.java)
            startActivity(intent)
        }
        binding.swichFragments.setOnClickListener {

            val fragInstance: Fragment? =
                parentFragmentManager.findFragmentById(binding.fragmentContainer.id)

            if (fragInstance is MapFragment) {
                Log.e(TAG, " is MapFragment")
                callListFragment()
            } else {
                Log.e(TAG, " not MapFragment")

                callMapFragment()
            }
        }
    }

    private fun callMapFragment() {
        Log.e(TAG, "callMapFragment")
        val fragment: Fragment = MapFragment()
        val fm: FragmentManager = parentFragmentManager
        val transaction: FragmentTransaction = fm.beginTransaction()
        transaction.replace(binding.fragmentContainer.id, fragment, "MapFragment")
        transaction.commit()
        binding.ivSwichFragments.setImageDrawable(activity?.getDrawable(R.drawable.ic_map_outline))
        binding.tvSwichFragments.text = getString(R.string.map)
        PrefsManager(requireContext()).storeSearchFragment(fragment::class.java.simpleName)
    }

    private fun callListFragment() {
        Log.e(TAG, "callListFragment")
        val fragment: Fragment = ListFragment()
        val fm: FragmentManager = parentFragmentManager
        val transaction: FragmentTransaction = fm.beginTransaction()
        transaction.replace(binding.fragmentContainer.id, fragment, "ListFragment")
        transaction.commit()
        binding.ivSwichFragments.setImageDrawable(activity?.getDrawable(R.drawable.ic_row_vertical))
        binding.tvSwichFragments.text = getString(R.string.list)
        PrefsManager(requireContext()).storeSearchFragment(fragment::class.java.simpleName)
    }

    override fun onStart() {
        super.onStart()
        Log.e(TAG, "onStart")

    }

    override fun onStop() {
        super.onStop()
        Log.e(TAG, "onStop")

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.e(TAG, "onAttach")
    }

    override fun onDetach() {
        super.onDetach()
        Log.e(TAG, "onDetach")
    }

    override fun onResume() {
        super.onResume()
        Log.e(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.e(TAG, "onPause")
    }


}