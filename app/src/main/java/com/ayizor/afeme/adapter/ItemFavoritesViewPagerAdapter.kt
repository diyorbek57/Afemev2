package com.ayizor.afeme.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ItemFavoritesViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    var framgnetList: ArrayList<Fragment> = ArrayList()
    var stringList: ArrayList<String> = ArrayList()
    public fun addFragment(fragment: Fragment, title: String) {
        framgnetList.add(fragment)
        stringList.add(title)

    }

    override fun getCount(): Int {
        return framgnetList.size
    }

    override fun getItem(position: Int): Fragment {
        return framgnetList[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return stringList[position]

    }

}