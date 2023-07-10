package com.toyproject.bookandmoviereview

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeBookFragment()
            1 -> HomeMovieFragment()
            else -> HomeBookFragment()
        }
    }

    override fun getItemCount(): Int {
        return 0
    }
}