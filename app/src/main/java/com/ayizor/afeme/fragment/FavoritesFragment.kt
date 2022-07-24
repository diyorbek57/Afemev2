package com.ayizor.afeme.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ayizor.afeme.R
import com.ayizor.afeme.adapter.ItemFavoritesViewPagerAdapter
import com.ayizor.afeme.databinding.FragmentFavoritesBinding
import com.ayizor.afeme.fragment.favoritesfragment.OffersFragment
import com.ayizor.afeme.fragment.favoritesfragment.SubscriptionsFragment
import com.google.android.material.tabs.TabLayout


class FavoritesFragment : Fragment() {

    lateinit var binding: FragmentFavoritesBinding
    val TAG: String = FavoritesFragment::class.java.simpleName
    lateinit var adapter: ItemFavoritesViewPagerAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        inits()
        return binding.root
    }

    private fun inits() {
        setupViewPager()
    }

    private fun setupViewPager() {
        adapter = ItemFavoritesViewPagerAdapter(childFragmentManager)
        adapter.addFragment(OffersFragment(), getString(R.string.offers))
        adapter.addFragment(SubscriptionsFragment(), getString(R.string.subscriptions))
        binding.vpFavorites.adapter = adapter
        binding.tlFavorites.setupWithViewPager(binding.vpFavorites)

        binding.tlFavorites.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val position = tab.position
                if (position == 1) {
                    binding.tvEdit.visibility = View.VISIBLE
                } else {
                    binding.tvEdit.visibility = View.GONE
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                val position = tab.position
                if (position == 1) {
                    binding.tvEdit.visibility = View.VISIBLE
                } else {
                    binding.tvEdit.visibility = View.GONE
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

    }


}