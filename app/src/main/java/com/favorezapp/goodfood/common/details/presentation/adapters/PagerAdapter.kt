package com.favorezapp.goodfood.common.details.presentation.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(
    private val bundle: Bundle,
    private val fragmentList: List<Fragment>,
    fragmentActivity: FragmentActivity
): FragmentStateAdapter(fragmentActivity) {
    private fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItemCount(): Int {
        return getCount()
    }

    override fun createFragment(position: Int): Fragment {
        fragmentList[position].arguments = bundle
        return fragmentList[position]
    }
}