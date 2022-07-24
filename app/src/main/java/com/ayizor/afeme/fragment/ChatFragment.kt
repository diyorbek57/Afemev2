package com.ayizor.afeme.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ayizor.afeme.R
import com.ayizor.afeme.adapter.ItemFavoritesViewPagerAdapter
import com.ayizor.afeme.databinding.FragmentChatBinding
import com.ayizor.afeme.fragment.chat.CallHistoryFragment
import com.ayizor.afeme.fragment.chat.MessagesFragment

class ChatFragment : Fragment() {


    lateinit var binding: FragmentChatBinding
    val TAG: String = FavoritesFragment::class.java.simpleName
    lateinit var adapter: ItemFavoritesViewPagerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(inflater, container, false)

        inits()
        return binding.root
    }

    private fun inits() {
        setupViewPager()
    }

    private fun setupViewPager() {
        adapter = ItemFavoritesViewPagerAdapter(childFragmentManager)
        adapter.addFragment(MessagesFragment(), getString(R.string.messages))
        adapter.addFragment(CallHistoryFragment(), getString(R.string.call_history))
        binding.vpChat.adapter = adapter
        binding.tlChat.setupWithViewPager(binding.vpChat)


    }
}