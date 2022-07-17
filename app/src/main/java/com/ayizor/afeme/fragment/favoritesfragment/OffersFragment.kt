package com.ayizor.afeme.fragment.favoritesfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ayizor.afeme.api.main.ApiInterface
import com.ayizor.afeme.databinding.FragmentOffersBinding
import com.ayizor.afeme.fragment.FavoritesFragment


class OffersFragment : Fragment() {

    lateinit var binding: FragmentOffersBinding
    val TAG: String = FavoritesFragment::class.java.simpleName
    var dataService: ApiInterface? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentOffersBinding.inflate(inflater, container, false)

        inits()
        return binding.root
    }

    private fun inits() {

    }


}